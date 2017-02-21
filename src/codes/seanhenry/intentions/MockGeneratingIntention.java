package codes.seanhenry.intentions;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MethodGatheringVisitor extends PsiRecursiveElementVisitor {

  private ArrayList<SwiftFunctionDeclaration> elements = new ArrayList<>();

  @Override
  public void visitElement(PsiElement element) {
    if (element instanceof SwiftFunctionDeclaration) {
      SwiftFunctionDeclaration declaration = (SwiftFunctionDeclaration) element;
      elements.add(declaration);
    }
    super.visitElement(element);
  }

  List<SwiftFunctionDeclaration> getElements() {
    return elements;
  }
}

public class MockGeneratingIntention extends PsiElementBaseIntentionAction implements IntentionAction {

  private Editor editor;

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
    //SwiftClassDeclaration classDeclaration = PsiTreeUtil.getParentOfType(psiElement, SwiftClassDeclaration.class);
    //return classDeclaration != null;
    return true;
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
    this.editor = editor;
    SwiftClassDeclaration classDeclaration = PsiTreeUtil.getParentOfType(psiElement, SwiftClassDeclaration.class);
    if (classDeclaration == null) {
      showErrorMessage("Could not find a class to mock.");
      return;
    }
    SwiftTypeInheritanceClause inheritanceClause = classDeclaration.getTypeInheritanceClause();
    if (inheritanceClause == null) {
      showErrorMessage("Mock class does not inherit from anything.");
      return;
    }
    if (inheritanceClause.getReferenceTypeElementList().isEmpty()) {
      showErrorMessage("Could not find a protocol reference.");
      return;
    }
    SwiftReferenceTypeElement protocol = inheritanceClause.getReferenceTypeElementList().get(0);
    deleteClassStatements(classDeclaration);
    List<SwiftFunctionDeclaration> protocolMethods = getProtocolMethods(protocol);
    addProtocolFunctionsToClass(protocolMethods, classDeclaration);
  }

  private void showErrorMessage(String message) {
    HintManager.getInstance().showErrorHint(editor, message);
  }

  private void deleteClassStatements(SwiftClassDeclaration classDeclaration) {
    for (SwiftStatement statement : classDeclaration.getStatementList()) {
      statement.delete();
    }
  }

  private List<SwiftFunctionDeclaration> getProtocolMethods(SwiftReferenceTypeElement protocol) {
    PsiElement resolved = protocol.resolve();
    if (resolved == null) {
      showErrorMessage("The protocol '" + protocol.getName() + "' could not be found.");
      return Collections.emptyList();
    }
    PsiFile resolvedFile = resolved.getContainingFile();
    MethodGatheringVisitor visitor = new MethodGatheringVisitor();
    resolvedFile.accept(visitor);
    return visitor.getElements();
  }

  private void addProtocolFunctionsToClass(List<SwiftFunctionDeclaration> functions, SwiftClassDeclaration classDeclaration) {
    for (SwiftFunctionDeclaration function : functions) {
      SwiftFunctionDeclaration functionWithBody = SwiftPsiElementFactory.getInstance(function).createFunction(function.getText() + "{ " + createInvokedVariableName(function) + " = true }");
      addInvocationCheckVariable(functionWithBody, classDeclaration);
      classDeclaration.addBefore(functionWithBody, classDeclaration.getLastChild());
    }
  }

  private void addInvocationCheckVariable(SwiftFunctionDeclaration function, SwiftClassDeclaration classDeclaration) {
    SwiftStatement variable = SwiftPsiElementFactory.getInstance(function).createStatement("var " + createInvokedVariableName(function) + " = false");
    classDeclaration.addBefore(variable, classDeclaration.getLastChild());
  }

  private String createInvokedVariableName(SwiftFunctionDeclaration function) {
    String functionName = function.getName();
    return "invoked" + functionName.substring(0, 1).toUpperCase() + functionName.substring(1);
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
}
