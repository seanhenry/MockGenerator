package codes.seanhenry.intentions;

import codes.seanhenry.helpers.*;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.testFramework.*;
import com.intellij.testFramework.fixtures.*;
import com.intellij.testFramework.fixtures.impl.TempDirTestFixtureImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;

import java.io.IOException;

public class MockGeneratingIntentionTest extends PlatformTestCase {

  private static final String dataPath = "/Users/sean/source/plugins/community/MockGenerator/testData/TestProject";
  private CodeInsightTestFixture myFixture;

  @Override
  protected void tearDown() throws Exception {
    try {
      myFixture.tearDown();
    } catch (Throwable ignored) {
    } finally {
      myFixture = null;
      try {
        super.tearDown();
      } catch (Throwable ignored) {}
    }
  }

  @Override
  protected void setUpProject() throws Exception {
    super.setUpProject();
    TempDirTestFixtureImpl tempDirTestFixture = new TempDirTestFixtureImpl();
    myFixture = IdeaTestFixtureFactory.getFixtureFactory().createCodeInsightFixture(new ImportProjectTestFixture(dataPath, "TestProject.xcodeproj", tempDirTestFixture), tempDirTestFixture);
    myFixture.setUp();
    myFixture.setTestDataPath(dataPath);
  }

  public Project getActiveProject() {
    return myFixture.getProject();
  }

  public void testAll() throws Exception {
    String[] fileNames = {
      "SimpleProtocol",
      "OptionalProtocol",
      "OverloadProtocol",
      "PropertyProtocol",
      "ClosureProtocol",
      "RecursiveProtocol",
      "MultipleProtocol",
      "DeepInheritance",
      "DiamondInheritanceProtocol",
      "AssociatedTypeProtocol",
      "MultiAssociatedTypeProtocol",
      //"UIKitProtocol",
      "PublicProtocol",
      "OpenProtocol",
      "DefaultValues",
    };

    for (String fileName : fileNames) {
      runTest(fileName);
    }
    Assert.assertFalse(isIntentionAvailable("NotAvailableInProductionCodeTarget"));
  }

  private boolean isIntentionAvailable(String fileName) {
    configureFile(fileName + ".swift");
    return myFixture.filterAvailableIntentions("Generate mock").size() > 0;
  }

  private void runTest(String fileName) throws IOException {
    String expectedFileName = fileName + "Mock_expected.swift";
    String mockFileName = fileName + "Mock.swift";
    System.out.println("Running test for " + fileName);
    PsiFile targetFile = configureFile(mockFileName);

    IntentionAction action = myFixture.findSingleIntention("Generate mock");

    WriteCommandAction.runWriteCommandAction(getActiveProject(), () -> action.invoke(getActiveProject(), myFixture.getEditor(), targetFile));
    myFixture.checkResultByFile(expectedFileName, true);
  }

  @NotNull
  private PsiFile configureFile(String mockFileName) {
    return myFixture.configureByFile(mockFileName);
  }
}
