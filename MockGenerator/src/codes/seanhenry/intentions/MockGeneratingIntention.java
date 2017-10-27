package codes.seanhenry.intentions;

import codes.seanhenry.mockgenerator.entities.ProtocolMethod;
import codes.seanhenry.mockgenerator.entities.ProtocolProperty;
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
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MockGeneratingIntention extends PsiElementBaseIntentionAction implements IntentionAction, ProjectComponent {

  private Editor editor;
  private SwiftClassDeclaration classDeclaration;
  private XcodeMockGenerator generator;

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
    SwiftClassDeclaration classDeclaration = findClassUnderCaret(psiElement);
    return classDeclaration != null && isElementInTestTarget(psiElement, project);
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
    this.editor = editor;
    classDeclaration = findClassUnderCaret(psiElement);
    if (classDeclaration == null) {
      showErrorMessage("Could not find a class to mock.");
      return;
    }
    SwiftTypeInheritanceClause inheritanceClause = classDeclaration.getTypeInheritanceClause();
    if (inheritanceClause == null) {
      showErrorMessage("Mock class does not inherit from anything.");
      return;
    }
    ProtocolItemFinder protocolItemFinder = new ProtocolItemFinder();
    protocolItemFinder.findItems(classDeclaration);
    if (protocolItemFinder.getProtocols().isEmpty()) {
      showErrorMessage("Could not find a protocol to mock.");
      return;
    }
    deleteClassStatements();
    new AssociatedTypeGenericConverter(classDeclaration)
        .convert(protocolItemFinder.getProtocols());
    generator = new XcodeMockGenerator();
    generator.setScope(getMockScope());
    addProtocolPropertiesToClass(protocolItemFinder.getProperties());
    addProtocolMethodsToClass(protocolItemFinder.getMethods());
    generateMock();
  }

  private SwiftClassDeclaration findClassUnderCaret(@NotNull PsiElement psiElement) {
    return PsiTreeUtil.getParentOfType(psiElement, SwiftClassDeclaration.class);
  }

  private void generateMock() {
    String propertiesString = generator.generate();
    try {
      PsiFile file = PsiFileFactory.getInstance(classDeclaration.getProject()).createFileFromText(SwiftLanguage.INSTANCE, propertiesString);
      for (PsiElement child : file.getChildren()) {
        appendInClass(child);
      }
    }
    catch (PsiInvalidElementAccessException e) {
      showErrorMessage("An unexpected error occurred: " + e.getMessage());
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

  private void showErrorMessage(String message) {
    HintManager.getInstance().showErrorHint(editor, message);
  }

  private void deleteClassStatements() {
    for (SwiftStatement statement : classDeclaration.getStatementList()) {
      statement.delete();
    }
  }

  private void addProtocolPropertiesToClass(List<SwiftVariableDeclaration> properties) {
    for (SwiftVariableDeclaration property : properties) {
      String name = property.getVariables().get(0).getName();
      String type = PsiTreeUtil.findChildOfType(property, SwiftTypeElement.class).getText();
      boolean hasSetter = PsiTreeUtil.findChildOfType(property, SwiftSetterClause.class) != null;
      generator.add(new ProtocolProperty(name, type, hasSetter, property.getText()));
    }
  }

  private void addProtocolMethodsToClass(List<SwiftFunctionDeclaration> methods) {
    for (SwiftFunctionDeclaration method : methods) {
      generator.add(new ProtocolMethod(
          getName(method),
          getReturnType(method),
          getParameterString(method),
          method.getText()
      ));
    }
  }

  @NotNull
  private String getParameterString(SwiftFunctionDeclaration method) {
    String parameterString = "";
    PsiElement parameterClause = method.getParameterClause();
    if (parameterClause != null && parameterClause.getText().length() > 1) {
      String parameterClauseText = parameterClause.getText();
      parameterString = parameterClauseText.substring(1, parameterClauseText.length() - 1);
    }
    return parameterString;
  }

  @Nullable
  private String getReturnType(SwiftFunctionDeclaration method) {
    String returnType = null;
    SwiftTypeElement returnObject = PsiTreeUtil.findChildOfType(method.getFunctionResult(), SwiftTypeElement.class);
    if (returnObject != null) {
      returnType = returnObject.getText();
    }
    return returnType;
  }

  private String getName(SwiftFunctionDeclaration method) {
    String name = "";
    if (method.getName() != null) {
      name = method.getName();
    }
    return name;
  }

  private void appendInClass(PsiElement element) {
    classDeclaration.addBefore(element, classDeclaration.getLastChild());
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
