package codes.seanhenry.intentions;

import codes.seanhenry.analytics.GoogleAnalyticsTracker;
import codes.seanhenry.analytics.Tracker;
import codes.seanhenry.mockgenerator.entities.*;
import codes.seanhenry.mockgenerator.entities.Class;
import codes.seanhenry.mockgenerator.generator.CallbackMockView;
import codes.seanhenry.mockgenerator.generator.Generator;
import codes.seanhenry.util.AssociatedTypeGenericConverter;
import codes.seanhenry.util.MySwiftPsiUtil;
import codes.seanhenry.util.finder.SwiftTypeItemFinder;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.common.collect.Lists;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.cidr.xcode.model.PBXProjectFile;
import com.jetbrains.cidr.xcode.model.PBXTarget;
import com.jetbrains.cidr.xcode.model.XcodeMetaData;
import com.jetbrains.swift.SwiftLanguage;
import com.jetbrains.swift.psi.*;
import one.util.streamex.Joining;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

public class MockGeneratingIntention extends PsiElementBaseIntentionAction implements IntentionAction, ProjectComponent {

  private SwiftClassDeclaration classDeclaration;
  static Tracker tracker = new GoogleAnalyticsTracker();

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
    SwiftClassDeclaration classDeclaration = findClassUnderCaret(psiElement);
    return classDeclaration != null && isElementInTestTarget(psiElement, project);
  }

  private SwiftClassDeclaration findClassUnderCaret(@NotNull PsiElement psiElement) {
    return PsiTreeUtil.getParentOfType(psiElement, SwiftClassDeclaration.class);
  }

  private static boolean isElementInTestTarget(PsiElement element, Project project) {
    VirtualFile containingFile = element.getContainingFile().getVirtualFile();
    List<PBXProjectFile> projectFiles = XcodeMetaData.getInstance(project).findAllContainingProjects(containingFile);
    for (PBXProjectFile projectFile : projectFiles) {
      List<PBXTarget> targets = projectFile.getTargetsFor(containingFile, PBXTarget.class, true);
      for (PBXTarget target : targets) {
        if (target.isAnyXCTestTests()) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
    classDeclaration = findClassUnderCaret(psiElement);
    CallbackMockView view = new CallbackMockView((model) -> {
      StringWriter writer = new StringWriter();
      DefaultMustacheFactory mf = new DefaultMustacheFactory();
      Mustache mustache = mf.compile("mock.mustache");
      try {
        mustache.execute(writer, model).flush();
      } catch (IOException ignored) {}
      String[] lines = new String(writer.getBuffer()).split("\n");
      // TODO: make own class and just return string
      return Arrays.stream(lines).map(String::trim).filter(it -> !it.isEmpty()).collect(Collectors.joining("\n"));
    });
    Generator generator = new Generator(view);
//    generator.setScope(getMockScope());
    SwiftTypeItemFinder protocolItemFinder;
    SwiftTypeItemFinder classItemFinder;
    try {
      validateClass();
      validateMockClassInheritance();
      generator.add(transformMockClass());
//      protocolItemFinder = getProtocolItemFinder();
//      classItemFinder = getClassItemFinder();
//      validateItems(classItemFinder, protocolItemFinder);
//      transformProtocolItems(protocolItemFinder, classItemFinder, generator);
//      transformClassItems(classItemFinder, generator);
      deleteClassStatements();
      addGenericClauseToMock();
      generateMock(generator, view);
      track("generated");
    } catch (Exception e) {
      e.printStackTrace();
      showErrorMessage(e.getMessage(), editor);
      track(e.getMessage());
    }
  }

  private Class transformMockClass() {
    // TODO: test cannot resolve
    // TODO: test no inheritance type (show error)
    // TODO: test cannot transform, maybe incorrectly add struct as inherited type?
    List<PsiElement> resolved = classDeclaration.getTypeInheritanceClause().getTypeElementList().stream().map(e -> Resolver.resolve(e)).collect(Collectors.toList());
    List<Protocol> protocols = resolved.stream().map(r -> Transformer.transformProtocol(r)).filter(it -> it != null).collect(Collectors.toList());
    Class superclass = null;
    if (resolved.size() > 0) {
     superclass = Transformer.transformClass(resolved.get(0));
    }
    return new Class(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), superclass, protocols, getMockScope());
  }

  private static class Resolver extends SwiftVisitor {

    @Nullable
    static PsiElement resolve(PsiElement element) {
      Resolver visitor = new Resolver();
      element.accept(visitor);
      return visitor.resolved;
    }

    private PsiElement resolved;

    @Override
    public void visitReferenceTypeElement(@NotNull SwiftReferenceTypeElement swiftReferenceTypeElement) {
      resolved = swiftReferenceTypeElement.resolve();
    }
  }

  private static class Transformer extends SwiftVisitor {

    static Protocol transformProtocol(PsiElement element) {
      Transformer visitor = new Transformer();
      element.accept(visitor);
      return visitor.transformedProtocol;
    }

    static Type transformType(PsiElement element) {
      Transformer visitor = new Transformer();
      element.accept(visitor);
      return visitor.transformedType;
    }

    static ResolvedType transformResolvedType(PsiElement element) {
      Transformer visitor = new Transformer();
      element.accept(visitor);
      // tODO: if no resolve
      // TODO: if no transform
      PsiElement resolved = Resolver.resolve(element);
      Type transformedResolved = visitor.transformedType;
      if (resolved != null) {
        Type r = transformType(resolved);
        if (r != null) {
          transformedResolved = r;
        }
      }
      return new ResolvedType(visitor.transformedType, transformedResolved);
    }

    static Object transformDeclaration(PsiElement element) {
      Transformer visitor = new Transformer();
      element.accept(visitor);
      return visitor.transformedDeclaration;
    }

    static Class transformClass(PsiElement element) {
      Transformer visitor = new Transformer();
      element.accept(visitor);
      return visitor.transformedClass;
    }

    private Class transformedClass;
    private Protocol transformedProtocol;
    private Object transformedDeclaration;
    private Type transformedType;

    @Override
    public void visitClassDeclaration(@NotNull SwiftClassDeclaration element) {
      List<Object> items = element.getStatementList().stream().map((s) -> transformDeclaration(s)).collect(Collectors.toList());
      List<Initializer> initializers = items.stream().filter((s) -> s instanceof Initializer).map((m) -> (Initializer) m).collect(Collectors.toList());
      List<Method> methods = items.stream().filter((s) -> s instanceof Method).map((m) -> (Method) m).collect(Collectors.toList());
      List<Property> properties = items.stream().filter((s) -> s instanceof Property).map((m) -> (Property) m).collect(Collectors.toList());
      // TODO: test cannot resolve
      List<PsiElement> resolved = new ArrayList<>();
      if (element.getTypeInheritanceClause() != null) {
        resolved = element.getTypeInheritanceClause().getTypeElementList().stream().map(e -> Resolver.resolve(e)).collect(Collectors.toList());
      }
      List<Protocol> protocols = resolved.stream().map(r -> Transformer.transformProtocol(r)).collect(Collectors.toList());
      transformedClass = new Class(initializers, properties, methods, null, protocols, null);
    }

    @Override
    public void visitProtocolDeclaration(@NotNull SwiftProtocolDeclaration element) {
      List<Object> items = element.getStatementList().stream().map((s) -> transformDeclaration(s)).collect(Collectors.toList());
      List<Initializer> initializers = items.stream().filter((s) -> s instanceof Initializer).map((m) -> (Initializer) m).collect(Collectors.toList());
      List<Method> methods = items.stream().filter((s) -> s instanceof Method).map((m) -> (Method) m).collect(Collectors.toList());
      List<Property> properties = items.stream().filter((s) -> s instanceof Property).map((m) -> (Property) m).collect(Collectors.toList());
      // TODO: test cannot resolve
      List<PsiElement> resolved = new ArrayList<>();
      if (element.getTypeInheritanceClause() != null) {
        resolved = element.getTypeInheritanceClause().getTypeElementList().stream().map(e -> Resolver.resolve(e)).collect(Collectors.toList());
      }
      List<Protocol> protocols = resolved.stream().map(r -> Transformer.transformProtocol(r)).collect(Collectors.toList());
      transformedProtocol = new Protocol(initializers, properties, methods, protocols);
    }

    @Override
    public void visitInitializerDeclaration(@NotNull SwiftInitializerDeclaration element) {
      List<Parameter> parameters = transformParameters(element.getParameterClause());
      transformedDeclaration = new Initializer(parameters, MySwiftPsiUtil.isFailable(element), element.isThrowing(), true);
    }

    @Override
    public void visitVariableDeclaration(@NotNull SwiftVariableDeclaration element) {
      SwiftPatternInitializer patternInitializer = element.getPatternInitializerList().get(0);
      SwiftTypeAnnotatedPattern pattern = (SwiftTypeAnnotatedPattern)patternInitializer.getPattern();
      Type type = transformType(pattern.getTypeAnnotation().getTypeElement());
      transformedDeclaration = new Property(patternInitializer.getPattern().getVariables().get(0).getName(), type, isWritable(element), getDeclarationText(element, type.getText()));
    }

    private boolean isWritable(SwiftVariableDeclaration element) {
      return PsiTreeUtil.findChildOfType(element, SwiftSetterClause.class) != null;
    }

    private String getDeclarationText(SwiftVariableDeclaration element, String type) {
      int startOffset = getStartOffset(element);
      int endOffset = getEndOffset(element);
      String signature = element.getContainingFile().getText().substring(startOffset, endOffset);
      if (MySwiftPsiUtil.hasExplicitType(element)) {
        return signature;
      }
      return signature + ": " + type;
    }

    private int getStartOffset(SwiftVariableDeclaration property) {
      return property.getTextOffset() + property.getAttributes().getTextLength();
    }

    private int getEndOffset(SwiftVariableDeclaration property) {
      SwiftInitializer initializer = PsiTreeUtil.findChildOfType(property, SwiftInitializer.class);
      int endOffset = property.getTextOffset() + property.getTextLength();
      if (initializer != null) {
        endOffset = initializer.getTextOffset();
      }
      return endOffset;
    }

    @Override
    public void visitFunctionDeclaration(@NotNull SwiftFunctionDeclaration element) {
      List<String> genericParameters = transformGenericParameters(element.getGenericParameterClause());
      ResolvedType resolvedReturnType = transformReturnType(element);
      String declarationText = getDeclarationText(element);
      List<Parameter> parameters = transformParameters(element.getParameterClause());
      transformedDeclaration = new Method(element.getName(), genericParameters, resolvedReturnType, parameters, declarationText, isThrowing(element.getThrowsClause()));
    }

    private List<String> transformGenericParameters(SwiftGenericParameterClause clause) {
      if (clause == null) {
        return new ArrayList<>();
      }
      return clause.getGenericParameterList().stream()
          .map(g -> g.getName())
          .collect(Collectors.toList());
    }

    private List<Parameter> transformParameters(SwiftParameterClause parameterClause) {
      return parameterClause.getParameterList().stream()
          .map(p -> new Parameter(p.getExternalNameIdentifier().getText(), p.getNameIdentifier().getText(), transformResolvedType(p.getTypeAnnotation().getTypeElement()), p.getText(), false))
          .collect(Collectors.toList());
    }

    private String getDeclarationText(SwiftFunctionDeclaration element) {
      SwiftCodeBlock codeBlock = element.getCodeBlock();
      int methodOffset = element.getStartOffsetInParent();
      int startOffset = methodOffset + element.getAttributes().getStartOffsetInParent() + element.getAttributes().getTextLength();
      int endOffset = methodOffset + element.getTextLength();
      if (codeBlock != null) {
        endOffset = methodOffset + codeBlock.getStartOffsetInParent();
      }
      return element.getContainingClass().getText().substring(startOffset, endOffset);
    }

    @NotNull
    private ResolvedType transformReturnType(@NotNull SwiftFunctionDeclaration func) {
      ResolvedType resolvedReturnType = ResolvedType.Companion.getIMPLICIT();
      if (func.getFunctionResult() != null && func.getFunctionResult().getTypeElement() != null) {
        Type returnType = transformType(func.getFunctionResult().getTypeElement());
        resolvedReturnType = new ResolvedType(returnType, returnType);
      }
      return resolvedReturnType;
    }

    @Override
    public void visitTypeElement(@NotNull SwiftTypeElement swiftTypeElement) {
      super.visitTypeElement(swiftTypeElement);
    }

    @Override
    public void visitReferenceTypeElement(@NotNull SwiftReferenceTypeElement element) {
      if (element.getGenericArgumentClause() == null) {
        transformedType = new TypeIdentifier(element.getText());
      } else {
        List<Type> genericTypes = element.getGenericArgumentClause().getTypeElementList().stream()
            .map(e -> transformType(e))
            .collect(Collectors.toList());
        transformedType = new GenericType(element.getName(), genericTypes);
      }
    }

    @Override
    public void visitOptionalTypeElement(@NotNull SwiftOptionalTypeElement element) {
      transformedType = new OptionalType(transformType(element.getTypeElement()), false, false);
    }

    @Override
    public void visitImplicitlyUnwrappedOptionalTypeElement(@NotNull SwiftImplicitlyUnwrappedOptionalTypeElement element) {
      transformedType = new OptionalType(transformType(element.getTypeElement()), true, false);
    }

    @Override
    public void visitArrayDictionaryTypeElement(@NotNull SwiftArrayDictionaryTypeElement element) {
      if (element.isDictionary()) {
        transformedType = new DictionaryType(transformType(element.getKeyTypeElement()), transformType(element.getValueTypeElement()), false);
      } else {
        transformedType = new ArrayType(transformType(element.getKeyTypeElement()), false);
      }
    }

    @Override
    public void visitTupleTypeElement(@NotNull SwiftTupleTypeElement element) {
      List<Type> elements = element.getTupleTypeItemList().stream()
          .map(i -> transformType(i.getTypeElement()))
          .collect(Collectors.toList());
      transformedType = new TupleType(elements);
    }

    @Override
    public void visitTupleTypeItem(@NotNull SwiftTupleTypeItem element) {
      transformedType = transformType(element.getTypeElement());
    }

    @Override
    public void visitFunctionTypeElement(@NotNull SwiftFunctionTypeElement element) {
      SwiftTupleTypeElement tuple = (SwiftTupleTypeElement)element.getTypeElementList().get(0);
      List<Type> arguments = tuple.getTupleTypeItemList().stream()
          .map(t -> transformType(t))
          .collect(Collectors.toList());
      Type returnType = transformType(element.getTypeElementList().get(1));
      transformedType = new FunctionType(arguments, returnType, isThrowing(element.getThrowsClause()));
    }

    private boolean isThrowing(SwiftThrowsClause clause) {
      if (clause != null) {
        return clause.isThrows();
      }
      return false;
    }

    @Override
    public void visitInoutTypeElement(@NotNull SwiftInoutTypeElement element) {
      transformedType = transformType(element.getTypeElement());
    }

    @Override
    public void visitTypeAliasDeclaration(@NotNull SwiftTypeAliasDeclaration element) {
      SwiftTypeElement type = element.getTypeAssignment().getTypeElement();
      PsiElement resolved = Resolver.resolve(type);
      if (resolved != null) {
        this.transformedType = transformType(resolved);
      } else {
        this.transformedType = transformType(type);
      }
    }

    @Override
    public void visitMetaTypeElement(@NotNull SwiftMetaTypeElement element) {
      transformedType = new TypeIdentifier(Arrays.asList(element.getText().split("\\.")));
    }
  }

  private String getMockScope() {
    if (MySwiftPsiUtil.isPublic(classDeclaration)) {
      return "public";
    } else if (MySwiftPsiUtil.isOpen(classDeclaration)) {
      return "open";
    }
    return "";
  }

  private void validateClass() throws Exception {
    if (classDeclaration == null) {
      throw new Exception("Could not find a class to mock.");
    }
  }

  private void validateMockClassInheritance() throws Exception {
    SwiftTypeInheritanceClause inheritanceClause = classDeclaration.getTypeInheritanceClause();
    if (inheritanceClause == null) {
      throw new Exception("Mock class does not inherit from anything.");
    }
  }

//  @NotNull
//  private SwiftTypeItemFinder getProtocolItemFinder() {
//    SwiftTypeItemFinder itemFinder = new SwiftTypeItemFinder(new ProtocolTypeChoosingStrategy(), new ClassTypeInitialiserChoosingStrategy(), new ProtocolPropertyChoosingStrategy(), new ProtocolMethodChoosingStrategy());
//    itemFinder.findItems(classDeclaration);
//    return itemFinder;
//  }
//
//  private SwiftTypeItemFinder getClassItemFinder() {
//    SwiftTypeItemFinder itemFinder = new SwiftTypeItemFinder(new ClassTypeChoosingStrategy(), new ClassTypeInitialiserChoosingStrategy(), new ClassPropertyChoosingStrategy(), new ClassMethodChoosingStrategy());
//    itemFinder.findItems(classDeclaration);
//    return itemFinder;
//  }
//
//  private void validateItems(SwiftTypeItemFinder classItemFinder, SwiftTypeItemFinder protocolItemFinder) throws Exception {
//    if (classItemFinder.getTypes().isEmpty() && protocolItemFinder.getTypes().isEmpty()) {
//      throw new Exception("Could not find an inherited type to mock.");
//    }
//  }
//
//  private void transformProtocolItems(SwiftTypeItemFinder protocolItemFinder, SwiftTypeItemFinder classItemFinder, MockGenerator generator) throws Exception {
//    ProtocolDuplicateRemover remover = new ProtocolDuplicateRemover(protocolItemFinder, classItemFinder);
//    SwiftTypeTransformer transformer = new SwiftProtocolTransformer(remover);
//    transformer.transform();
//    generator.addInitialisers(transformer.getInitializers());
//    generator.addProperties(transformer.getProperties());
//    generator.addMethods(transformer.getMethods());
//  }
//
//  private void transformClassItems(SwiftTypeItemFinder itemFinder, MockGenerator generator) throws Exception {
//    SwiftTypeTransformer transformer = new SwiftClassTransformer(itemFinder);
//    transformer.transform();
//    generator.setClassInitialisers(transformer.getInitializers());
//    generator.addClassProperties(transformer.getProperties());
//    generator.addClassMethods(transformer.getMethods());
//  }
//
  private void addGenericClauseToMock() {
    new AssociatedTypeGenericConverter(classDeclaration).convert();
  }

  private void deleteClassStatements() {
    PsiElement firstElement = findFirstClassElement();
    PsiElement lastElement = findLastClassElement();
    if (firstElement != null && lastElement != null) {
      classDeclaration.deleteChildRange(firstElement, lastElement);
    }
  }

  private PsiElement findFirstClassElement() {
    if (classDeclaration.getStatementList().isEmpty()) {
      return null;
    }
    return classDeclaration.getStatementList().get(0);
  }

  private PsiElement findLastClassElement() {
    if (classDeclaration.getStatementList().isEmpty()) {
      return null;
    }
    return classDeclaration.getStatementList().get(classDeclaration.getStatementList().size() - 1);
  }

  private void generateMock(Generator generator, CallbackMockView view) throws Exception {
    generator.generate();
    String string = view.getResult().stream().collect(Joining.with("\n"));
    try {
      PsiFile file = PsiFileFactory.getInstance(classDeclaration.getProject()).createFileFromText(SwiftLanguage.INSTANCE, string);
      for (PsiElement child : file.getChildren()) {
        appendInClass(child);
      }
    }
    catch (PsiInvalidElementAccessException e) {
      throw new Exception("An unexpected error occurred: " + e.getMessage());
    }
  }

  private void appendInClass(PsiElement element) {
    classDeclaration.addBefore(element, classDeclaration.getLastChild());
  }

  private void showErrorMessage(String message, Editor editor) {
    if (message == null) {
      message = "An unknown error occurred.";
    }
    HintManager.getInstance().showErrorHint(editor, message);
  }

  private void track(String action) {
    ProgressManager.getInstance().executeNonCancelableSection(() -> tracker.track(action));
  }

  @Nls
  @NotNull
  @Override
  public String getFamilyName() {
    return getText();
  }

  @NotNull
  @Override
  public String getText() {
    return "Generate mock";
  }

  @Override
  public void projectOpened() {
  }

  @Override
  public void projectClosed() {
  }

  @Override
  public void initComponent() {
  }

  @Override
  public void disposeComponent() {
  }

  @NotNull
  @Override
  public String getComponentName() {
    return "Swift Mock Generator for AppCode";
  }
}
