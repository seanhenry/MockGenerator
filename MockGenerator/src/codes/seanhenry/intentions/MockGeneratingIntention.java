package codes.seanhenry.intentions;

import codes.seanhenry.generator.ProtocolItemTransformer;
import codes.seanhenry.mockgenerator.xcode.XcodeMockGenerator;
import codes.seanhenry.util.*;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.editor.Editor;
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
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MockGeneratingIntention extends PsiElementBaseIntentionAction implements IntentionAction, ProjectComponent {

  private SwiftClassDeclaration classDeclaration;

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
    XcodeMockGenerator generator = new XcodeMockGenerator();
    generator.setScope(getMockScope());
    ProtocolItemFinder protocolItemFinder;
    try {
      protocolItemFinder = getProtocolItemFinder();
      transformProtocolItems(protocolItemFinder, generator);
      deleteClassStatements();
      addGenericClauseToMock(protocolItemFinder);
      generateMock(generator);
    } catch (Exception e) {
      showErrorMessage(e.getMessage(), editor);
    }
  }

  private String getMockScope() {
    if (classDeclaration.getAttributes().getText().contains("public")) {
      return "public";
    } else if (classDeclaration.getAttributes().getText().contains("open")) {
      return "open";
    }
    return "";
  }

  private ProtocolItemFinder getProtocolItemFinder() throws Exception {
    validateClass();
    validateInheritedProtocol();
    return validateProtocolItems();
  }

  private void validateClass() throws Exception {
    if (classDeclaration == null) {
      throw new Exception("Could not find a class to mock.");
    }
  }

  private void validateInheritedProtocol() throws Exception {
    SwiftTypeInheritanceClause inheritanceClause = classDeclaration.getTypeInheritanceClause();
    if (inheritanceClause == null) {
      throw new Exception("Mock class does not inherit from anything.");
    }
  }

  @NotNull
  private ProtocolItemFinder validateProtocolItems() throws Exception {
    ProtocolItemFinder protocolItemFinder = new ProtocolItemFinder();
    protocolItemFinder.findItems(classDeclaration);
    if (protocolItemFinder.getProtocols().isEmpty()) {
      throw new Exception("Could not find a protocol to mock.");
    }
    return protocolItemFinder;
  }

  private void transformProtocolItems(ProtocolItemFinder protocolItemFinder, XcodeMockGenerator generator) throws Exception {
    new ProtocolItemTransformer(protocolItemFinder, generator)
        .transform();
  }

  private void addGenericClauseToMock(ProtocolItemFinder protocolItemFinder) {
    new AssociatedTypeGenericConverter(classDeclaration)
        .convert(protocolItemFinder.getProtocols());
  }

  private void deleteClassStatements() {
    for (SwiftStatement statement : classDeclaration.getStatementList()) {
      statement.delete();
    }
  }

  private void generateMock(XcodeMockGenerator generator) throws Exception {
    String propertiesString = generator.generate();
    try {
      PsiFile file = PsiFileFactory.getInstance(classDeclaration.getProject()).createFileFromText(SwiftLanguage.INSTANCE, propertiesString);
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
    HintManager.getInstance().showErrorHint(editor, message);
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
