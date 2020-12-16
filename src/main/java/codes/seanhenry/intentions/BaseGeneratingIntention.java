package codes.seanhenry.intentions;

import codes.seanhenry.error.DefaultErrorPresenter;
import codes.seanhenry.error.ErrorPresenter;
import codes.seanhenry.error.MockGeneratorException;
import codes.seanhenry.mockgenerator.entities.Class;
import codes.seanhenry.mockgenerator.entities.MockClass;
import codes.seanhenry.mockgenerator.entities.Protocol;
import codes.seanhenry.mockgenerator.generator.Generator;
import codes.seanhenry.template.MustacheView;
import codes.seanhenry.transformer.ClassTransformer;
import codes.seanhenry.transformer.ProtocolTransformer;
import codes.seanhenry.transformer.Resolver;
import codes.seanhenry.util.AssociatedTypeGenericConverter;
import codes.seanhenry.util.MySwiftPsiUtil;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiInvalidElementAccessException;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
//import com.jetbrains.cidr.xcode.model.PBXProjectFile;
//import com.jetbrains.cidr.xcode.model.PBXTarget;
//import com.jetbrains.cidr.xcode.model.XcodeMetaData;
//import com.jetbrains.swift.SwiftLanguage;
import com.jetbrains.swift.SwiftLanguage;
import com.jetbrains.swift.psi.SwiftClassDeclaration;
import com.jetbrains.swift.psi.SwiftTypeElement;
import com.jetbrains.swift.psi.SwiftTypeInheritanceClause;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class BaseGeneratingIntention extends PsiElementBaseIntentionAction implements IntentionAction, ProjectComponent {

  private SwiftClassDeclaration classDeclaration;
  static ErrorPresenter errorPresenter = new DefaultErrorPresenter();

  protected abstract String getMockType();

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
    SwiftClassDeclaration classDeclaration = findClassUnderCaret(psiElement);
    return classDeclaration != null && isElementInTestTarget(psiElement, project);
  }

  private SwiftClassDeclaration findClassUnderCaret(@NotNull PsiElement psiElement) {
    return PsiTreeUtil.getParentOfType(psiElement, SwiftClassDeclaration.class);
  }

  private static boolean isElementInTestTarget(PsiElement element, Project project) {
    // TODO: can't find PBXProject since 2020.3
//    VirtualFile containingFile = element.getContainingFile().getVirtualFile();
//    List<PBXProjectFile> projectFiles = XcodeMetaData.getInstance(project).findAllContainingProjects(containingFile);
//    for (PBXProjectFile projectFile : projectFiles) {
//      List<PBXTarget> targets = projectFile.getTargetsFor(containingFile, PBXTarget.class, true);
//      for (PBXTarget target : targets) {
//        if (target.isAnyXCTestTests()) {
//          return true;
//        }
//      }
//    }
//    return false;
    return true;
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
    classDeclaration = findClassUnderCaret(psiElement);
    MustacheView view = new MustacheView(getMockType());
    Generator generator = new Generator(view);
    try {
      validateMockClassInheritance();
      List<PsiElement> resolved = resolveInheritanceClause(editor);
      generator.set(transformMockClass(resolved));
      deleteClassStatements();
      addGenericClauseToMock();
      generateMock(generator, view);
    } catch (MockGeneratorException e) {
      showErrorMessage(e.getMessage(), editor);
    } catch (Exception e) {
      throw e;
    }
  }

  private List<PsiElement> resolveInheritanceClause(Editor editor) {
    SwiftTypeInheritanceClause inheritanceClause = classDeclaration.getTypeInheritanceClause();
    List<PsiElement> resolved = new ArrayList<>();
    List<SwiftTypeElement> unresolved = new ArrayList<>();
    for (SwiftTypeElement e: inheritanceClause.getTypeElementList()) {
      PsiElement r = Resolver.Companion.resolve(e);
      if (r != null) {
        resolved.add(r);
      } else {
        unresolved.add(e);
      }
    }
    reportResolveInheritanceClauseError(resolved, unresolved, editor);
    return resolved;
  }

  private void reportResolveInheritanceClauseError(List<PsiElement> resolved, List<SwiftTypeElement> unresolved, Editor editor) {
    String errorMessage = listTypeNames(unresolved) + " could not be resolved.";
    if (!unresolved.isEmpty()) {
      if (resolved.isEmpty()) {
        throw new MockGeneratorException(errorMessage);
      } else {
        showErrorMessage(errorMessage, editor);
      }
    }
  }

  private String listTypeNames(List<SwiftTypeElement> types) {
    return types.stream().map(PsiElement::getText).collect(Collectors.joining(", "));
  }

  private MockClass transformMockClass(List<PsiElement> resolved) {
    List<Protocol> protocols = transformProtocols(resolved);
    Class superclass = null;
    if (resolved.size() > 0) {
     superclass = transformSuperclass(resolved.get(0));
    }
    return new MockClass(superclass, protocols, getMockScope());
  }

  private Class transformSuperclass(PsiElement firstType) {
    return ClassTransformer.Companion.transform(firstType);
  }

  private List<Protocol> transformProtocols(List<PsiElement> resolved) {
    return resolved.stream().map(ProtocolTransformer.Companion::transform).filter(Objects::nonNull).collect(Collectors.toList());
  }

  private String getMockScope() {
    if (MySwiftPsiUtil.isPublic(classDeclaration)) {
      return "public";
    } else if (MySwiftPsiUtil.isOpen(classDeclaration)) {
      return "open";
    }
    return "";
  }

  private void validateMockClassInheritance() {
    SwiftTypeInheritanceClause inheritanceClause = classDeclaration.getTypeInheritanceClause();
    if (inheritanceClause == null) {
      throw new MockGeneratorException("Mock class does not inherit from anything.");
    }
  }

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
    if (classDeclaration.getDeclarations().isEmpty()) {
      return null;
    }
    return classDeclaration.getDeclarations().get(0);
  }

  private PsiElement findLastClassElement() {
    if (classDeclaration.getDeclarations().isEmpty()) {
      return null;
    }
    return classDeclaration.getDeclarations().get(classDeclaration.getDeclarations().size() - 1);
  }

  private void generateMock(Generator generator, MustacheView view) {
    generator.generate();
    String string = view.getResult();
    try {
      PsiFile file = PsiFileFactory.getInstance(classDeclaration.getProject()).createFileFromText(SwiftLanguage.INSTANCE, string);
      for (PsiElement child : file.getChildren()) {
        appendInClass(child);
      }
    }
    catch (PsiInvalidElementAccessException e) {
      throw new MockGeneratorException("An unexpected error occurred: " + e.getMessage());
    }
  }

  private void appendInClass(PsiElement element) {
    classDeclaration.addBefore(element, classDeclaration.getLastChild());
  }

  private void showErrorMessage(String message, Editor editor) {
    if (message == null) {
      message = "An unknown error occurred.";
    }
    errorPresenter.show(message, editor);
  }

  @Nls
  @NotNull
  @Override
  public String getFamilyName() {
    return getText();
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
