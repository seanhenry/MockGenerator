package codes.seanhenry.intentions;

import codes.seanhenry.util.AppendStringDecorator;
import codes.seanhenry.util.PrependStringDecorator;
import codes.seanhenry.util.StringDecorator;
import codes.seanhenry.util.UniqueMethodNameGenerator;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
  private UniqueMethodNameGenerator methodNameGenerator;

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
    SwiftClassDeclaration classDeclaration = PsiTreeUtil.getParentOfType(psiElement, SwiftClassDeclaration.class);
    return classDeclaration != null;
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
    methodNameGenerator = new UniqueMethodNameGenerator(getMethodModels(functions));
    for (SwiftFunctionDeclaration function : functions) {
      SwiftFunctionDeclaration functionWithBody = SwiftPsiElementFactory.getInstance(function).createFunction(function.getText() + "{ }");
      addInvokedCheckExpression(functionWithBody);
      addInvokedParameterExpression(functionWithBody);
      addReturnExpression(functionWithBody);
      addInvocationCheckVariable(functionWithBody, classDeclaration);
      addInvokedParameterVariables(functionWithBody, classDeclaration);
      addReturnVariable(functionWithBody, classDeclaration);
      classDeclaration.addBefore(functionWithBody, classDeclaration.getLastChild());
    }
  }

  private void addInvocationCheckVariable(SwiftFunctionDeclaration function, SwiftClassDeclaration classDeclaration) {
    SwiftStatement variable = SwiftPsiElementFactory.getInstance(function).createStatement("var " + createInvokedVariableName(function) + " = false");
    classDeclaration.addBefore(variable, classDeclaration.getLastChild());
  }

  private void addInvokedParameterVariables(SwiftFunctionDeclaration function, SwiftClassDeclaration classDeclaration) {
    List<String> parameters = getParameterNames(function, p -> p.getName() + ": " + getType(p.getParameterTypeAnnotation(), false));
    if (parameters.isEmpty()) {
      return;
    } else if (parameters.size() == 1) {
      parameters.add("Void");
    }
    String variable = "var " + createInvokedParametersName(function) + ": (" + String.join(", ", parameters) + ")?";
    SwiftStatement statement = SwiftPsiElementFactory.getInstance(function).createStatement(variable);
    classDeclaration.addBefore(statement, classDeclaration.getLastChild());
  }

  private void addReturnVariable(SwiftFunctionDeclaration function, SwiftClassDeclaration classDeclaration) {
    if (function.getFunctionResult() == null) {
      return;
    }
    String name = createStubbedVariableName(function);
    SwiftStatement variable = SwiftPsiElementFactory.getInstance(function).createStatement("var " + name + ": " + getType(function.getFunctionResult()) + "!");
    classDeclaration.addBefore(variable, classDeclaration.getLastChild());
  }

  private void addInvokedCheckExpression(SwiftFunctionDeclaration function) {
    SwiftExpression expression = SwiftPsiElementFactory.getInstance(function).createExpression(createInvokedVariableName(function) + " = true ", function);
    function.getCodeBlock().addBefore(expression, function.getCodeBlock().getLastChild());
  }

  private void addInvokedParameterExpression(SwiftFunctionDeclaration function) {
    List<String> parameters = getParameterNames(function, PsiNamedElement::getName);
    if (parameters.isEmpty()) {
      return;
    } else if (parameters.size() == 1) {
      parameters.add("()");
    }

    String string = createInvokedParametersName(function) + " = (" + String.join(", ", parameters) + ")";
    SwiftExpression expression = SwiftPsiElementFactory.getInstance(function).createExpression(string, function);
    function.getCodeBlock().addBefore(expression, function.getCodeBlock().getLastChild());
  }

  private void addReturnExpression(SwiftFunctionDeclaration function) {
    if (function.getFunctionResult() == null) {
      return;
    }
    SwiftStatement statement = SwiftPsiElementFactory.getInstance(function).createStatement("return " + createStubbedVariableName(function));
    function.getCodeBlock().addBefore(statement, function.getCodeBlock().getLastChild());
  }

  private String createInvokedVariableName(SwiftFunctionDeclaration function) {
    StringDecorator prependDecorator = new PrependStringDecorator(null, "invoked");
    String name = methodNameGenerator.generate(getFunctionID(function));
    return prependDecorator.process(name);
  }

  private String createStubbedVariableName(SwiftFunctionDeclaration function) {
    StringDecorator prependDecorator = new PrependStringDecorator(null, "stubbed");
    StringDecorator appendDecorator = new AppendStringDecorator(prependDecorator, "Result");
    String name = methodNameGenerator.generate(getFunctionID(function));
    return appendDecorator.process(name);
  }

  private String createInvokedParametersName(SwiftFunctionDeclaration function) {
    StringDecorator prependDecorator = new PrependStringDecorator(null, "invoked");
    StringDecorator appendDecorator = new AppendStringDecorator(prependDecorator, "Parameters");
    String name = methodNameGenerator.generate(getFunctionID(function));
    return appendDecorator.process(name);
  }

  private String getType(PsiElement element) {
    return getType(element, true);
  }

  private String getType(PsiElement element, boolean removeOptional) {
    SwiftTypeElement type = PsiTreeUtil.findChildOfType(element, SwiftTypeElement.class);
    if (type == null) return null;
    SwiftTypeElement nextType = PsiTreeUtil.findChildOfType(type, SwiftTypeElement.class);
    boolean isOptional = type instanceof SwiftImplicitlyUnwrappedOptionalTypeElement || type instanceof SwiftOptionalTypeElement;
    if (nextType != null && removeOptional && isOptional) {
      return nextType.getText();
    }
    return type.getText();
  }

  private List<String> getParameterNames(SwiftFunctionDeclaration function, Function<SwiftParameter, String> operation) {
    return function.getParameterClauseList().stream()
        .map(SwiftParameterClause::getParameterList)
        .flatMap(Collection::stream)
        .map(operation)
        .collect(Collectors.toList());
  }

  private List<UniqueMethodNameGenerator.MethodModel> getMethodModels(List<SwiftFunctionDeclaration> functions) {

    return functions.stream()
      .map(this::toMethodModel)
      .collect(Collectors.toList());
  }

  private UniqueMethodNameGenerator.MethodModel toMethodModel(SwiftFunctionDeclaration function) {
    return new UniqueMethodNameGenerator.MethodModel(
      getFunctionID(function),
      function.getName(),
      getParameterNames(function, p -> toParameterLabel(p)).toArray(new String[]{})
    );
  }

  private String toParameterLabel(SwiftParameter parameter) {
    return parameter.getText();
  }

  private String getFunctionID(SwiftFunctionDeclaration function) {
    return function.getName() + String.join(":", getParameterNames(function, p -> p.getText()));
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
