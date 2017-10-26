package codes.seanhenry.intentions;

import codes.seanhenry.mockgenerator.entities.ProtocolMethod;
import codes.seanhenry.mockgenerator.entities.ProtocolProperty;
import codes.seanhenry.mockgenerator.util.*;
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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    List<SwiftProtocolDeclaration> protocols = getResolvedProtocols(classDeclaration);
    protocols = removeDuplicates(protocols);
    protocols = removeNSObjectProtocol(protocols);
    if (protocols.isEmpty()) {
      showErrorMessage("Could not find a protocol to mock.");
      return;
    }
    deleteClassStatements();
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
    methods = removeDuplicates(methods);
    properties = removeDuplicates(properties);
    generator = new XcodeMockGenerator();
    generator.setScope(getMockScope());
    addProtocolPropertiesToClass(properties);
    addProtocolMethodsToClass(methods);
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

  private void addProtocolPropertiesToClass(List<SwiftVariableDeclaration> properties) {
    for (SwiftVariableDeclaration property : properties) {
      String name = property.getVariables().get(0).getName();
      String type = PsiTreeUtil.findChildOfType(property, SwiftTypeElement.class).getText();
      boolean hasSetter = PsiTreeUtil.findChildOfType(property, SwiftSetterClause.class) != null;
      generator.add(new ProtocolProperty(name, type, hasSetter, property.getText()));
    }
  }

  private void addProtocolMethodsToClass(List<SwiftFunctionDeclaration> methods) { // TODO: rename to methods
    for (SwiftFunctionDeclaration method : methods) {
      String name = "";
      if (method.getName() != null) {
        name = method.getName();
      }
      String returnType = null;
      SwiftTypeElement returnObject = PsiTreeUtil.findChildOfType(method.getFunctionResult(), SwiftTypeElement.class);
      if (returnObject != null) {
        returnType = returnObject.getText();
      }
      String parameterString = "";
      PsiElement parameterClause = method.getParameterClause();
      if (parameterClause != null && parameterClause.getText().length() > 1) {
        String parameterClauseText = parameterClause.getText();
        parameterString = parameterClauseText.substring(1, parameterClauseText.length() - 1);
      }
      generator.add(new ProtocolMethod(name, returnType, parameterString, method.getText()));
    }
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

  private void appendInClass(PsiElement element) {
    classDeclaration.addBefore(element, classDeclaration.getLastChild());
  }

  @NotNull
  private SwiftPsiElementFactory getElementFactory() {
    return SwiftPsiElementFactory.getInstance(classDeclaration);
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
