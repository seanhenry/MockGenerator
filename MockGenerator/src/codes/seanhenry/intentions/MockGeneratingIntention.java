package codes.seanhenry.intentions;

import codes.seanhenry.transformer.SwiftClassTransformer;
import codes.seanhenry.transformer.SwiftProtocolTransformer;
import codes.seanhenry.transformer.SwiftTypeTransformer;
import codes.seanhenry.mockgenerator.xcode.MockGenerator;
import codes.seanhenry.util.AssociatedTypeGenericConverter;
import codes.seanhenry.util.MySwiftPsiUtil;
import codes.seanhenry.util.finder.ProtocolDuplicateRemover;
import codes.seanhenry.util.finder.SwiftTypeItemFinder;
import codes.seanhenry.util.finder.initialiser.ClassTypeInitialiserChoosingStrategy;
import codes.seanhenry.util.finder.initialiser.EmptyInitialiserChoosingStrategy;
import codes.seanhenry.util.finder.methods.ClassMethodChoosingStrategy;
import codes.seanhenry.util.finder.methods.ProtocolMethodChoosingStrategy;
import codes.seanhenry.util.finder.properties.ClassPropertyChoosingStrategy;
import codes.seanhenry.util.finder.properties.ProtocolPropertyChoosingStrategy;
import codes.seanhenry.util.finder.types.ClassTypeChoosingStrategy;
import codes.seanhenry.util.finder.types.ProtocolTypeChoosingStrategy;
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
    MockGenerator generator = new MockGenerator();
    generator.setScope(getMockScope());
    SwiftTypeItemFinder protocolItemFinder;
    SwiftTypeItemFinder classItemFinder;
    try {
      validateClass();
      validateMockClassInheritance();
      protocolItemFinder = getProtocolItemFinder();
      classItemFinder = getClassItemFinder();
      validateItems(classItemFinder, protocolItemFinder);
      transformProtocolItems(protocolItemFinder,classItemFinder, generator);
      transformClassItems(classItemFinder, generator);
      deleteClassStatements();
      addGenericClauseToMock(protocolItemFinder);
      generateMock(generator);
    } catch (Exception e) {
      showErrorMessage(e.getMessage(), editor);
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

  @NotNull
  private SwiftTypeItemFinder getProtocolItemFinder() {
    SwiftTypeItemFinder itemFinder = new SwiftTypeItemFinder(new ProtocolTypeChoosingStrategy(), new EmptyInitialiserChoosingStrategy(), new ProtocolPropertyChoosingStrategy(), new ProtocolMethodChoosingStrategy());
    itemFinder.findItems(classDeclaration);
    return itemFinder;
  }

  private SwiftTypeItemFinder getClassItemFinder() {
    SwiftTypeItemFinder itemFinder = new SwiftTypeItemFinder(new ClassTypeChoosingStrategy(), new ClassTypeInitialiserChoosingStrategy(), new ClassPropertyChoosingStrategy(), new ClassMethodChoosingStrategy());
    itemFinder.findItems(classDeclaration);
    return itemFinder;
  }

  private void validateItems(SwiftTypeItemFinder classItemFinder, SwiftTypeItemFinder protocolItemFinder) throws Exception {
    if (classItemFinder.getTypes().isEmpty() && protocolItemFinder.getTypes().isEmpty()) {
      throw new Exception("Could not find an inherited type to mock.");
    }
  }

  private void transformProtocolItems(SwiftTypeItemFinder protocolItemFinder, SwiftTypeItemFinder classItemFinder, MockGenerator generator) throws Exception {
    ProtocolDuplicateRemover remover = new ProtocolDuplicateRemover(protocolItemFinder, classItemFinder);
    SwiftTypeTransformer transformer = new SwiftProtocolTransformer(remover);
    transformer.transform();
    generator.addProperties(transformer.getProperties());
    generator.addMethods(transformer.getMethods());
  }

  private void transformClassItems(SwiftTypeItemFinder itemFinder, MockGenerator generator) throws Exception {
    SwiftTypeTransformer transformer = new SwiftClassTransformer(itemFinder);
    transformer.transform();
    if (transformer.getInitialiser() != null) {
      generator.setInitialiser(transformer.getInitialiser());
    }
    generator.addClassProperties(transformer.getProperties());
    generator.addClassMethods(transformer.getMethods());
  }

  private void addGenericClauseToMock(SwiftTypeItemFinder protocolItemFinder) {
    new AssociatedTypeGenericConverter(classDeclaration)
        .convert(protocolItemFinder.getTypes());
  }

  private void deleteClassStatements() {
    for (SwiftStatement statement : classDeclaration.getStatementList()) {
      statement.delete();
    }
  }

  private void generateMock(MockGenerator generator) throws Exception {
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
    if (message == null) {
      message = "An unknown error occurred.";
    }
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
