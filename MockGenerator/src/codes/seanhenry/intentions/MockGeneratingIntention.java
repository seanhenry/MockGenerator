package codes.seanhenry.intentions;

import codes.seanhenry.mockgenerator.entities.BoolPropertyDeclaration;
import codes.seanhenry.mockgenerator.entities.IntPropertyDeclaration;
import codes.seanhenry.helpers.DefaultValueStore;
import codes.seanhenry.helpers.KeywordsStore;
import codes.seanhenry.mockgenerator.swift.BoolPropertyAssignmentToSwift;
import codes.seanhenry.mockgenerator.swift.BoolPropertyDeclarationToSwift;
import codes.seanhenry.mockgenerator.swift.IntPropertyDeclarationToSwift;
import codes.seanhenry.mockgenerator.swift.IntPropertyIncrementAssignmentToSwift;
import codes.seanhenry.mockgenerator.usecases.CreateInvocationCheck;
import codes.seanhenry.mockgenerator.usecases.CreateInvocationCount;
import codes.seanhenry.mockgenerator.util.AppendStringDecorator;
import codes.seanhenry.mockgenerator.util.PrependStringDecorator;
import codes.seanhenry.mockgenerator.util.StringDecorator;
import codes.seanhenry.mockgenerator.util.UniqueMethodNameGenerator;
import codes.seanhenry.util.*;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.cidr.xcode.model.PBXProjectFile;
import com.jetbrains.cidr.xcode.model.PBXTarget;
import com.jetbrains.cidr.xcode.model.XcodeMetaData;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MockGeneratingIntention extends PsiElementBaseIntentionAction implements IntentionAction, ProjectComponent {

  private Editor editor;
  private final StringDecorator invokedPropertyNameDecorator = new PrependStringDecorator(null, "invoked");
  private final StringDecorator stubbedPropertyNameDecorator = new PrependStringDecorator(null, "stubbed");
  private SwiftClassDeclaration classDeclaration;
  private SwiftFunctionDeclaration implementedFunction;
  private SwiftFunctionDeclaration protocolFunction;
    private DefaultValueStore defaultValueStore = new DefaultValueStore();
    private final KeywordsStore keywordsStore = new KeywordsStore();
  private String name = "";
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
  private final StringDecorator methodParametersListNameDecorator;
  {
    StringDecorator prependDecorator = new PrependStringDecorator(null, "invoked");
    methodParametersListNameDecorator = new AppendStringDecorator(prependDecorator, "ParametersList");
  }
  private final StringDecorator stubbedClosureResultNameDecorator;
  {
    StringDecorator prependDecorator = new PrependStringDecorator(null, "stubbed");
    stubbedClosureResultNameDecorator = new AppendStringDecorator(prependDecorator, "Result");
  }
  private final StringDecorator invokedPropertySetterListDecorator = new PrependStringDecorator(new AppendStringDecorator(null, "List"), "invoked");

  private String scope = "";

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
    SwiftClassDeclaration classDeclaration = PsiTreeUtil.getParentOfType(psiElement, SwiftClassDeclaration.class);
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
    classDeclaration = PsiTreeUtil.getParentOfType(psiElement, SwiftClassDeclaration.class);
    if (classDeclaration == null) {
      showErrorMessage("Could not find a class to mock.");
      return;
    }
    SwiftTypeInheritanceClause inheritanceClause = classDeclaration.getTypeInheritanceClause();
    if (inheritanceClause == null) {
      showErrorMessage("Mock class does not inherit from anything.");
      return;
    }
    List<SwiftProtocolDeclaration> protocols = getResolvedProtocols(classDeclaration);
    protocols = removeDuplicates(protocols);
    protocols = removeNSObjectProtocol(protocols);
    if (protocols.isEmpty()) {
      showErrorMessage("Could not find a protocol reference.");
      return;
    }
    deleteClassStatements();
    scope = getMockScope();
    List<SwiftVariableDeclaration> properties = protocols
      .stream()
      .flatMap(p -> getProtocolProperties(p).stream())
      .collect(Collectors.toList());
    List<SwiftFunctionDeclaration> methods = protocols
      .stream()
      .flatMap(p -> getProtocolMethods(p).stream())
      .collect(Collectors.toList());
    List<SwiftAssociatedTypeDeclaration> associatedTypes = protocols
      .stream()
      .flatMap(p -> getProtocolAssociatedTypes(p).stream())
      .collect(Collectors.toList());
    addGenericParametersToClass(removeDuplicates(associatedTypes));
    addProtocolPropertiesToClass(removeDuplicates(properties));
    addProtocolFunctionsToClass(removeDuplicates(methods));

    CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(psiElement.getManager());
    codeStyleManager.reformat(classDeclaration);
  }

  private String getMockScope() {
    if (classDeclaration.getAttributes().getText().contains("public")) {
      return "public ";
    } else if (classDeclaration.getAttributes().getText().contains("open")) {
      return "open ";
    }
    return "";
  }

  private static List<SwiftProtocolDeclaration> removeNSObjectProtocol(List<SwiftProtocolDeclaration> protocols) {
    return protocols.stream().filter(p -> !Objects.equals(p.getName(), "NSObjectProtocol")).collect(Collectors.toList());
  }

  private static <T> List<T> removeDuplicates(List<T> list) {
    return new ArrayList<>(new LinkedHashSet<>(list));
  }

  private void showErrorMessage(String message) {
    HintManager.getInstance().showErrorHint(editor, message);
  }

  private void deleteClassStatements() {
    for (SwiftStatement statement : classDeclaration.getStatementList()) {
      statement.delete();
    }
  }

  private static List<SwiftFunctionDeclaration> getProtocolMethods(PsiElement protocol) {
    ElementGatheringVisitor<SwiftFunctionDeclaration> visitor = new ElementGatheringVisitor<>(SwiftFunctionDeclaration.class);
    protocol.accept(visitor);
    return visitor.getElements();
  }

  private static List<SwiftVariableDeclaration> getProtocolProperties(PsiElement protocol) {
    ElementGatheringVisitor<SwiftVariableDeclaration> visitor = new ElementGatheringVisitor<>(SwiftVariableDeclaration.class);
    protocol.accept(visitor);
    return visitor.getElements();
  }

  private static List<SwiftAssociatedTypeDeclaration> getProtocolAssociatedTypes(PsiElement protocol) {
    ElementGatheringVisitor<SwiftAssociatedTypeDeclaration> visitor = new ElementGatheringVisitor<>(SwiftAssociatedTypeDeclaration.class);
    protocol.accept(visitor);
    return visitor.getElements();
  }

  private List<SwiftProtocolDeclaration> getResolvedProtocols(SwiftTypeDeclaration typeDeclaration) {
    SwiftTypeInheritanceClause inheritanceClause = typeDeclaration.getTypeInheritanceClause();
    if (inheritanceClause == null) {
      return Collections.emptyList();
    }
    List<SwiftProtocolDeclaration> results = inheritanceClause.getReferenceTypeElementList()
      .stream()
      .map(this::getResolvedProtocol)
      .filter(Objects::nonNull)
      .collect(Collectors.toList());
    results.addAll(results
      .stream()
      .flatMap(p -> getResolvedProtocols(p).stream())
      .collect(Collectors.toList()));
    return results;
  }

  private SwiftProtocolDeclaration getResolvedProtocol(SwiftReferenceTypeElement reference) {
    PsiElement element = reference.resolve();
    if (element == null) {
      showErrorMessage("The protocol '" + reference.getName() + "' could not be found.");
      return null;
    }
    if (element instanceof SwiftProtocolDeclaration) {
      return (SwiftProtocolDeclaration) element;
    } else if (element instanceof SwiftClassDeclaration
        && "NSObject".equals(((SwiftClassDeclaration)element).getName())) {
      // ignore NSObject
    } else {
      showErrorMessage("This plugin currently only supports protocols.");
    }
    return null;
  }

  private void addProtocolFunctionsToClass(List<SwiftFunctionDeclaration> functions) {
    UniqueMethodNameGenerator methodNameGenerator = new UniqueMethodNameGenerator(getMethodModels(functions));
    methodNameGenerator.generateMethodNames();
    for (SwiftFunctionDeclaration function : functions) {
      protocolFunction = function;
      implementedFunction = createImplementedFunction();
      name = methodNameGenerator.getMethodName(getFunctionID(protocolFunction));
      addInvokedCheckExpression();
      addInvokedCountExpression();
      addInvokedParameterExpressions();
      addCallToClosure();
      addReturnExpression();
      addInvocationCheckVariable();
      addInvocationCountVariable();
      addInvokedParameterVariables();
      addClosureResultVariables();
      addReturnVariable();
      appendInClass(implementedFunction);
    }
  }

  private SwiftFunctionDeclaration createImplementedFunction() {
    String name = getUnescapedProtocolFunctionName();
    List<String> params = getParameterNames(protocolFunction, p -> constructParameter(p), false);
    String literal = scope + "func " + name + "(";
    literal += String.join(", ", params);
    literal += ")";
    if (protocolFunction.getFunctionResult() != null)
      literal += " " + protocolFunction.getFunctionResult().getText();
    literal += " { }";
    return getElementFactory().createFunction(literal);
  }

  private String getUnescapedProtocolFunctionName() {
    String name = protocolFunction.getName();
    if (protocolFunction.getText().contains("`" + name + "`")) {
      name = "`" + name + "`";
    }
    return name;
  }
    
  private static String constructParameter(SwiftParameter parameter) {
    List<String> labels = PsiTreeUtil.findChildrenOfAnyType(parameter, SwiftIdentifierPattern.class, SwiftWildcardPattern.class)
      .stream()
      .map(p -> p.getText())
      .collect(Collectors.toList());
    String labelString = String.join(" ", labels);
    return labelString + ": " + MySwiftPsiUtil.getResolvedTypeName(parameter, false);
  }

  private void addProtocolPropertiesToClass(List<SwiftVariableDeclaration> properties) {
    for (SwiftVariableDeclaration property : properties) {
        
      name = MySwiftPsiUtil.getUnescapedPropertyName(property);
      String getterName = name + "Getter";
      String setterName = name + "Setter";
      SwiftVariableDeclaration invokedPropertySetterCheck = createInvocationCheckDeclaration(setterName);
      SwiftVariableDeclaration invokedPropertySetterCount = createInvocationCountDeclaration(setterName);
      SwiftVariableDeclaration invokedProperty = new PropertyDecorator(invokedPropertyNameDecorator, PropertyDecorator.OPTIONAL, scope)
        .decorate(property);
      SwiftVariableDeclaration invokedPropertyList = (SwiftVariableDeclaration)getElementFactory().createStatement(scope + "var " + invokedPropertySetterListDecorator.process(
        name) + " = [" + MySwiftPsiUtil.getPropertyTypeAnnotation(property).getTypeElement().getText() + "]()");

      SwiftVariableDeclaration invokedPropertyGetterCheck = createInvocationCheckDeclaration(getterName);
      SwiftVariableDeclaration invokedPropertyGetterCount = createInvocationCountDeclaration(getterName);
      String stubbedName = stubbedPropertyNameDecorator.process(name);
      SwiftTypeElement type = PsiTreeUtil.findChildOfType(property, SwiftTypeElement.class);
      SwiftVariableDeclaration stubbedProperty = buildStubbedVariable(stubbedName, type, MySwiftPsiUtil.getResolvedTypeName(property, true));
      boolean hasSetter = PsiTreeUtil.findChildOfType(property, SwiftSetterClause.class) != null;
      String literal = buildConcreteProperty(property, setterName, getterName, invokedProperty, invokedPropertyList, stubbedProperty, hasSetter);
      SwiftVariableDeclaration concreteProperty = (SwiftVariableDeclaration)getElementFactory().createStatement(literal);

      appendInClass(invokedPropertySetterCheck, hasSetter);
      appendInClass(invokedPropertySetterCount, hasSetter);
      appendInClass(invokedProperty, hasSetter);
      appendInClass(invokedPropertyList, hasSetter);
      appendInClass(invokedPropertyGetterCheck);
      appendInClass(invokedPropertyGetterCount);
      appendInClass(stubbedProperty);
      appendInClass(concreteProperty);
    }
  }

  private SwiftVariableDeclaration createInvocationCheckDeclaration(String name) {
    BoolPropertyDeclaration invokedSetterCheck = new CreateInvocationCheck(false).transform(name);
    String swiftString = new BoolPropertyDeclarationToSwift().transform(invokedSetterCheck);
    return (SwiftVariableDeclaration)getElementFactory().createStatement(scope + swiftString);
  }

  private SwiftVariableDeclaration createInvocationCountDeclaration(String name) {
    IntPropertyDeclaration declaration = new CreateInvocationCount().transform(name);
    String swiftString = new IntPropertyDeclarationToSwift().transform(declaration);
    return (SwiftVariableDeclaration)getElementFactory().createStatement(scope + swiftString);
  }

  private static String createInvocationCheckAssignment(String name) {
    BoolPropertyDeclaration invokedSetterCheck = new CreateInvocationCheck(true).transform(name);
    return new BoolPropertyAssignmentToSwift().transform(invokedSetterCheck, true);
  }

  private static String createInvocationCountIncrementExpression(String name) {
    IntPropertyDeclaration incrementExpression = new CreateInvocationCount().transform(name);
    return new IntPropertyIncrementAssignmentToSwift().transform(incrementExpression);
  }

  private void addGenericParametersToClass(List<SwiftAssociatedTypeDeclaration> associatedTypes) {

    if (associatedTypes.isEmpty()) {
      return;
    }
    if (classDeclaration.getGenericParameterClause() != null) {
      classDeclaration.getGenericParameterClause().delete();
    }
    String literal = "<";
    literal += associatedTypes.stream().map(PsiNamedElement::getName).collect(Collectors.joining(", "));
    literal += ">";
    SwiftStatement statement = getElementFactory().createStatement(literal);
    classDeclaration.addBefore(statement, classDeclaration.getTypeInheritanceClause());
  }

  @NotNull
  private String buildConcreteProperty(SwiftVariableDeclaration property,
                                       String setterName,
                                       String getterName,
                                       SwiftVariableDeclaration invokedProperty,
                                       SwiftVariableDeclaration invokedPropertyList,
                                       SwiftVariableDeclaration stubbedProperty, boolean hasSetter) {
    SwiftTypeAnnotatedPattern pattern = (SwiftTypeAnnotatedPattern) property.getPatternInitializerList().get(0).getPattern();
    String attributes = property.getAttributes().getText();
    String label = pattern.getPattern().getText();
    String literal = scope + attributes + " var " + label + pattern.getTypeAnnotation().getText() + "{\n";
    String getterStatements =
      createInvocationCheckAssignment(getterName) + "\n" +
      createInvocationCountIncrementExpression(getterName) + "\n" +
      "return " + MySwiftPsiUtil.getName(stubbedProperty) + "\n";
    if (hasSetter) {
      literal += "set {\n" +
                 createInvocationCheckAssignment(setterName) + "\n" +
                 createInvocationCountIncrementExpression(setterName) + "\n" +
                 MySwiftPsiUtil.getName(invokedProperty) + " = newValue\n" +
                 MySwiftPsiUtil.getName(invokedPropertyList) + ".append(newValue)\n" +
                 "}\n";
      literal += "get {\n" +
                 getterStatements +
                 "}\n";
    } else {
      literal += getterStatements;
    }
    literal += "}";
    return literal;
  }

  private void appendInClass(PsiElement element) {
    appendInClass(element, true);
  }

  private void appendInClass(PsiElement element, boolean shouldAppend) {
    if (shouldAppend) {
      classDeclaration.addBefore(element, classDeclaration.getLastChild());
    }
  }

  private void appendInImplementedFunction(PsiElement element) {
    SwiftCodeBlock codeBlock = implementedFunction.getCodeBlock();
    if (codeBlock != null && codeBlock.getLastChild() != null) {
      implementedFunction.getCodeBlock().addBefore(element, codeBlock.getLastChild());
    }
  }

  private void addInvocationCheckVariable() {
    appendInClass(createInvocationCheckDeclaration(name));
  }

  private void addInvocationCountVariable() {
    appendInClass(createInvocationCountDeclaration(name));
  }

  private void addInvokedParameterVariables() {
    List<String> parameters = getParameterNames(protocolFunction, p -> {
      String typeName = MySwiftPsiUtil.getResolvedTypeNameRemovingInout(p);
      String name = getUnescapedParameterName(p) + ": " + typeName;
      if (MySwiftPsiUtil.isOptional(p)) {
        return name + "?";
      }
      return name;
    }, true);
    if (parameters.isEmpty()) {
      return;
    } else if (parameters.size() == 1) {
      parameters.add("Void");
    }
    String variable = scope + "var " + createInvokedParametersName() + ": (" + String.join(", ", parameters) + ")?";
    SwiftStatement statement = getElementFactory().createStatement(variable);
    appendInClass(statement);

    String listVariable = scope + "var " + createInvokedParametersListName() + " = [(" + String.join(", ", parameters) + ")]()";
    SwiftStatement listStatement = getElementFactory().createStatement(listVariable);
    appendInClass(listStatement);
  }

  private void addClosureResultVariables() {
    List<SwiftParameter> parameters = getClosureParameters();
    for (SwiftParameter parameter : parameters) {
      String name = parameter.getName();
      List<String> types = getClosureParameterTypes(parameter);
      String variable = scope + "var " + createClosureResultName(name) + ": ";
      if (types.isEmpty()) {
        continue;
      }
      if (types.size() == 1) {
        types.add("Void");
      }

      variable += "(" + String.join(", ", types) + ")?";
      SwiftStatement statement = getElementFactory().createStatement(variable);
      appendInClass(statement);
    }
  }

  @NotNull
  private SwiftPsiElementFactory getElementFactory() {
    return SwiftPsiElementFactory.getInstance(classDeclaration);
  }

  private static List<String> getClosureParameterTypes(SwiftParameter parameter) {
    SwiftFunctionTypeElement closure = MySwiftPsiUtil.findResolvedType(parameter, SwiftFunctionTypeElement.class);
    SwiftTupleTypeElement firstTuple = PsiTreeUtil.findChildOfType(closure, SwiftTupleTypeElement.class);
    return PsiTreeUtil.findChildrenOfType(firstTuple, SwiftTupleTypeItem.class).stream().map(t -> t.getTypeElement().getText())
      .collect(Collectors.toList());
  }

  private void addReturnVariable() {
    SwiftFunctionResult result = protocolFunction.getFunctionResult();
    if (result == null) {
      return;
    }
    String resultString = MySwiftPsiUtil.getResolvedTypeName(result);
    if (isClosure(result) && !result.getTypeElement().getText().startsWith("((")) {
      resultString = "(" + resultString + ")";
    }
    String name = createStubbedVariableName();
    SwiftStatement variable = buildStubbedVariable(name, result.getTypeElement(), resultString);
    appendInClass(variable);
  }

  private SwiftVariableDeclaration buildStubbedVariable(String name, SwiftTypeElement typeElement, String typeString) {
    String defaultValueAssignment = "";
    String defaultValue = defaultValueStore.getDefaultValue(typeElement);
    if (defaultValue != null && !defaultValue.isEmpty()) {
      defaultValueAssignment = " = " + defaultValue;
    }
    String literal = scope + "var " + name + ": " + typeString + "!" + defaultValueAssignment;
    return (SwiftVariableDeclaration)getElementFactory().createStatement(literal);
  }

  private void addInvokedCheckExpression() {
    SwiftExpression expression = getElementFactory().createExpression(createInvocationCheckAssignment(name), protocolFunction);
    appendInImplementedFunction(expression);
  }

  private void addInvokedCountExpression() {
    SwiftExpression expression = getElementFactory().createExpression(createInvocationCountIncrementExpression(name), protocolFunction);
    appendInImplementedFunction(expression);
  }

  private void addInvokedParameterExpressions() {
    List<String> parameters = getEscapedParameterNames(protocolFunction, PsiNamedElement::getName, true);
    if (parameters.isEmpty()) {
      return;
    } else if (parameters.size() == 1) {
      parameters.add("()");
    }

    String string = createInvokedParametersName() + " = (" + String.join(", ", parameters) + ")";
    SwiftExpression expression = getElementFactory().createExpression(string, protocolFunction);
    appendInImplementedFunction(expression);

    string = createInvokedParametersListName() + ".append((" + String.join(", ", parameters) + "))";
    expression = getElementFactory().createExpression(string, protocolFunction);
    appendInImplementedFunction(expression);
  }

  private void addCallToClosure() {
    for (SwiftParameter parameter : getClosureParameters()) {
      int count = getClosureParameterTypes(parameter).size();
      String name = parameter.getName();
      String closureCall;
      String optional = MySwiftPsiUtil.isOptional(parameter) ? "?" : "";
      String suppressWarning = getSuppressWarningString(parameter);

      if (count == 0) {
        closureCall = suppressWarning + name + optional + "()";
      } else {
        closureCall = "if let result = " + createClosureResultName(name) + " {";
        closureCall += suppressWarning + name + optional + "(";
        closureCall += IntStream.range(0, count).mapToObj(i -> "result." + i).collect(Collectors.joining(","));
        closureCall += ") }";
      }
      PsiElement statement = getElementFactory().createStatement(closureCall, protocolFunction);
      appendInImplementedFunction(statement);
    }
  }

  private static String getSuppressWarningString(SwiftParameter parameter) {
    SwiftFunctionTypeElement closure = MySwiftPsiUtil.findResolvedType(parameter, SwiftFunctionTypeElement.class);
    String suppressWarning = "";
    if (closure != null && closure.getTypeElementList().size() > 1) {
      SwiftTypeElement typeElement = closure.getTypeElementList().get(closure.getTypeElementList().size() - 1);
      suppressWarning = MySwiftPsiUtil.isVoid(typeElement) ? "" : "_ = ";
    }
    return suppressWarning;
  }

  private void addReturnExpression() {
    if (protocolFunction.getFunctionResult() == null) {
      return;
    }
    SwiftStatement statement = getElementFactory().createStatement("return " + createStubbedVariableName());
    appendInImplementedFunction(statement);
  }

  private String createClosureResultName(String name) {
    return new PrependStringDecorator(stubbedClosureResultNameDecorator, protocolFunction.getName())
      .process(name);
  }

  private String createStubbedVariableName() {
    return stubMethodNameDecorator.process(name);
  }

  private String createInvokedParametersName() {
    return methodParametersNameDecorator.process(name);
  }

  private String createInvokedParametersListName() {
    return methodParametersListNameDecorator.process(name);
  }

  private List<String> getEscapedParameterNames(SwiftFunctionDeclaration function, Function<SwiftParameter, String> operation, boolean shouldRemoveClosures) {
    return streamParameterNames(function, operation, shouldRemoveClosures)
      .map(p -> escapeSwiftKeyword(p))
      .collect(Collectors.toList());
  }

  private String escapeSwiftKeyword(String input) {
    if (keywordsStore.isSwiftKeyword(input)) {
      return "`" + input + "`";
    }
    return input;
  }

  private static List<String> getParameterNames(SwiftFunctionDeclaration function,
                                                Function<SwiftParameter, String> operation,
                                                boolean shouldRemoveClosures) {
    return streamParameterNames(function, operation, shouldRemoveClosures)
      .collect(Collectors.toList());
  }

  private static Stream<String> streamParameterNames(SwiftFunctionDeclaration function,
                                                     Function<SwiftParameter, String> operation,
                                                     boolean shouldRemoveClosures) {
    Predicate<? super SwiftParameter> filter = p -> true;
    if (shouldRemoveClosures) {
      filter = p -> !isClosure(p);
    }
    return function.getParameterClause()
        .getParameterList()
        .stream()
        .filter(filter)
        .map(operation);
  }

  private String getUnescapedParameterName(SwiftParameter parameter) {
    String name = parameter.getName();
    if (parameter.getText().contains("`" + name + "`")) {
      name = "`" + name + "`";
    }
    return name;
  }

  private List<SwiftParameter> getClosureParameters() {
    return protocolFunction.getParameterClause()
        .getParameterList()
        .stream()
        .filter(MockGeneratingIntention::isClosure)
        .collect(Collectors.toList());
  }

  private static boolean isClosure(PsiElement parameter) {
    return getClosure(parameter) != null;
  }

  private static SwiftFunctionTypeElement getClosure(PsiElement element) {
    return MySwiftPsiUtil.findResolvedType(element, SwiftFunctionTypeElement.class);
  }

  private static List<UniqueMethodNameGenerator.MethodModel> getMethodModels(List<SwiftFunctionDeclaration> functions) {

    return functions.stream()
      .map(MockGeneratingIntention::toMethodModel)
      .collect(Collectors.toList());
  }

  private static UniqueMethodNameGenerator.MethodModel toMethodModel(SwiftFunctionDeclaration function) {
    return new UniqueMethodNameGenerator.MethodModel(
      getFunctionID(function),
      function.getName(),
      getParameterNames(function, p -> toParameterLabel(p), false).toArray(new String[]{})
    );
  }

  private static String toParameterLabel(SwiftParameter parameter) {
    return parameter.getText();
  }

  private static String getFunctionID(SwiftFunctionDeclaration function) {
    return function.getName() + String.join(":", getParameterNames(function, p -> p.getText(), false));
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