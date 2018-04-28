package codes.seanhenry.intentions;

import codes.seanhenry.analytics.GoogleAnalyticsTracker;
import codes.seanhenry.analytics.Tracker;
import codes.seanhenry.mockgenerator.entities.Class;
import codes.seanhenry.mockgenerator.entities.Protocol;
import codes.seanhenry.mockgenerator.generator.CallbackMockView;
import codes.seanhenry.mockgenerator.generator.Generator;
import codes.seanhenry.transformer.ClassTransformer;
import codes.seanhenry.transformer.ProtocolTransformer;
import codes.seanhenry.transformer.Resolver;
import codes.seanhenry.util.AssociatedTypeGenericConverter;
import codes.seanhenry.util.MySwiftPsiUtil;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiInvalidElementAccessException;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.cidr.xcode.model.PBXProjectFile;
import com.jetbrains.cidr.xcode.model.PBXTarget;
import com.jetbrains.cidr.xcode.model.XcodeMetaData;
import com.jetbrains.swift.SwiftLanguage;
import com.jetbrains.swift.psi.SwiftClassDeclaration;
import com.jetbrains.swift.psi.SwiftTypeInheritanceClause;
import one.util.streamex.Joining;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    try {
      validateClass();
      validateMockClassInheritance();
      generator.set(transformMockClass());
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
    // TODO: test NSObjectProtocol
    List<PsiElement> resolved = classDeclaration.getTypeInheritanceClause().getTypeElementList().stream().map(e -> Resolver.Companion.resolve(e)).collect(Collectors.toList());
    List<Protocol> protocols = resolved.stream().map(r -> ProtocolTransformer.Companion.transform(r)).filter(it -> it != null).collect(Collectors.toList());
    Class superclass = null;
    if (resolved.size() > 0) {
     superclass = ClassTransformer.Companion.transform(resolved.get(0));
    }
    return new Class(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), superclass, protocols, getMockScope());
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
