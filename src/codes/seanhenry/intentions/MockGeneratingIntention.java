package codes.seanhenry.intentions;

import codes.seanhenry.util.*;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MockGeneratingIntention extends PsiElementBaseIntentionAction implements IntentionAction {

  private Editor editor;
  private UniqueMethodNameGenerator methodNameGenerator;
  private final StringDecorator invokedPropertyNameDecorator = new PrependStringDecorator(null, "invoked");
  private final StringDecorator stubbedPropertyNameDecorator = new PrependStringDecorator(null, "stubbed");
  private final StringDecorator invokedMethodNameDecorator = new PrependStringDecorator(null, "invoked");
  private final StringDecorator stubMethodNameDecorator;
  {
    StringDecorator prependDecorator = new PrependStringDecorator(null, "stubbed");
    stubMethodNameDecorator = new AppendStringDecorator(prependDecorator, "Result");
  }
  private final StringDecorator methodParametersNameDecorator;
  {
    StringDecorator prependDecorator = new PrependStringDecorator(null, "invoked");
    methodParametersNameDecorator = new AppendStringDecorator(prependDecorator, "Parameters");
  }

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
    PsiElement resolvedProtocol = getResolvedProtocol(protocol);
    List<SwiftVariableDeclaration> protocolProperties = getProtocolProperties(resolvedProtocol);
    List<SwiftFunctionDeclaration> protocolMethods = getProtocolMethods(resolvedProtocol);
    addProtocolPropertiesToClass(protocolProperties, classDeclaration);
    addProtocolFunctionsToClass(protocolMethods, classDeclaration);

    CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(psiElement.getManager());
    codeStyleManager.reformat(classDeclaration);
  }

  private void showErrorMessage(String message) {
    HintManager.getInstance().showErrorHint(editor, message);
  }

  private void deleteClassStatements(SwiftClassDeclaration classDeclaration) {
    for (SwiftStatement statement : classDeclaration.getStatementList()) {
      statement.delete();
    }
  }

  private List<SwiftFunctionDeclaration> getProtocolMethods(PsiElement protocol) {
    ElementGatheringVisitor<SwiftFunctionDeclaration> visitor = new ElementGatheringVisitor<>(SwiftFunctionDeclaration.class);
    protocol.accept(visitor);
    return visitor.getElements();
  }

  private List<SwiftVariableDeclaration> getProtocolProperties(PsiElement protocol) {
    ElementGatheringVisitor<SwiftVariableDeclaration> visitor = new ElementGatheringVisitor<>(SwiftVariableDeclaration.class);
    protocol.accept(visitor);
    return visitor.getElements();
  }

  private PsiElement getResolvedProtocol(SwiftReferenceTypeElement protocol) {
    PsiElement resolved = protocol.resolve();
    if (resolved == null) {
      showErrorMessage("The protocol '" + protocol.getName() + "' could not be found.");
    }
    return resolved;
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

  private void addProtocolPropertiesToClass(List<SwiftVariableDeclaration> properties, SwiftClassDeclaration classDeclaration) {
    for (SwiftVariableDeclaration property : properties) {

      SwiftVariableDeclaration invokedProperty = new PropertyDecorator(invokedPropertyNameDecorator, PropertyDecorator.OPTIONAL)
        .decorate(property);
      SwiftVariableDeclaration stubbedProperty = new PropertyDecorator(stubbedPropertyNameDecorator, PropertyDecorator.IMPLICITLY_UNWRAPPED_OPTIONAL)
        .decorate(property);

      boolean hasSetter = PsiTreeUtil.findChildOfType(property, SwiftSetterClause.class) != null;
      SwiftTypeAnnotatedPattern pattern = (SwiftTypeAnnotatedPattern) property.getPatternInitializerList().get(0).getPattern();
      String attributes = property.getAttributes().getText();
      String label = pattern.getPattern().getText();
      String literal = attributes + " var " + label + pattern.getTypeAnnotation().getText() + "{\n";
      String returnLabel = "return " + MySwiftPsiUtil.getName(stubbedProperty) + "\n";
      if (hasSetter) {
        literal += "set {\n" +
                     MySwiftPsiUtil.getName(invokedProperty) + " = newValue\n" +
                   "}\n";
        literal += "get {\n" +
                     returnLabel +
                   "}\n";
        classDeclaration.addBefore(invokedProperty, classDeclaration.getLastChild());
      } else {
        literal += returnLabel;
      }
      literal += "}";

      classDeclaration.addBefore(stubbedProperty, classDeclaration.getLastChild());

      SwiftVariableDeclaration concreteProperty = (SwiftVariableDeclaration) SwiftPsiElementFactory.getInstance(property).createStatement(literal);
      classDeclaration.addBefore(concreteProperty, classDeclaration.getLastChild());
    }
  }

  private void addInvocationCheckVariable(SwiftFunctionDeclaration function, SwiftClassDeclaration classDeclaration) {
    SwiftStatement variable = SwiftPsiElementFactory.getInstance(function).createStatement("var " + createInvokedVariableName(function) + " = false");
    classDeclaration.addBefore(variable, classDeclaration.getLastChild());
  }

  private void addInvokedParameterVariables(SwiftFunctionDeclaration function, SwiftClassDeclaration classDeclaration) {
    List<String> parameters = getParameterNames(function, p -> p.getName() + ": " + MySwiftPsiUtil.getType(p.getParameterTypeAnnotation(), false));
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
    SwiftStatement variable = SwiftPsiElementFactory.getInstance(function).createStatement("var " + name + ": " + MySwiftPsiUtil.getType(function.getFunctionResult()) + "!");
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
    String name = methodNameGenerator.generate(getFunctionID(function));
    return invokedMethodNameDecorator.process(name);
  }

  private String createStubbedVariableName(SwiftFunctionDeclaration function) {
    String name = methodNameGenerator.generate(getFunctionID(function));
    return stubMethodNameDecorator.process(name);
  }

  private String createInvokedParametersName(SwiftFunctionDeclaration function) {
    String name = methodNameGenerator.generate(getFunctionID(function));
    return methodParametersNameDecorator.process(name);
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
