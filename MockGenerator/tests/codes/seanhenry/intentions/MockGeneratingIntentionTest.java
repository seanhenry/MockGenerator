package codes.seanhenry.intentions;

import codes.seanhenry.analytics.Tracker;
import codes.seanhenry.testhelpers.ImportProjectTestCase;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;

import java.io.IOException;

public class MockGeneratingIntentionTest extends ImportProjectTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    MockGeneratingIntention.tracker = new TrackerSpy();
  }

  @Override
  protected String getDataPath() {
    return "testData/TestProject";
  }

  @Override
  protected String getProjectFileName() {
    return "TestProject.xcodeproj";
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
//      "UIKitProtocol",
      "PublicProtocol",
      "OpenProtocol",
      "DefaultValues",
      "Keywords",
      "ThrowingProtocol",
      "TypealiasProtocol",
      "InitialiserProtocol",
      "GenericMethod",

      "SimpleClass",
      "UnoverridableClass",
      "InferredTypeClass",
      "PropertyClass",
      "ArgumentInitialiserClass",
      "RequiredInitialiserClass",
      "FailableInitialiserClass",
      "EmptyFailableInitialiserClass",
      "Superclass",
      "NSObjectClass",
      "InternalClass",
      "OpenClass",
      "PublicClass",
      "AvailableClass",
      "ThrowingClass",

      "Delete",
      "ClassAndProtocol",
    };

    for (String fileName : fileNames) {
      runTest(fileName);
    }
    Assert.assertFalse(isIntentionAvailable("NotAvailableInProductionCodeTarget"));
    runSuccessAnalyticsTest();
  }

  private void runSuccessAnalyticsTest() throws Exception {
    TrackerSpy trackerSpy = new TrackerSpy();
    MockGeneratingIntention.tracker = trackerSpy;
    runTest("SimpleProtocol");
    Assert.assertEquals("generated", trackerSpy.invokedTrackAction);
  }

  private boolean isIntentionAvailable(String fileName) {
    configureFile(fileName + ".swift");
    return getFixture().filterAvailableIntentions("Generate mock").size() > 0;
  }

  private void runTest(String fileName) throws IOException {
    String expectedFileName = fileName + "Mock_expected.swift";
    String mockFileName = fileName + "Mock.swift";
    System.out.println("Running test for " + fileName);
    PsiFile targetFile = configureFile(mockFileName);
    invokeIntention("Generate mock", targetFile);
    assertFilesEqual(expectedFileName, mockFileName);
  }

  @NotNull
  private PsiFile configureFile(String mockFileName) {
    return getFixture().configureByFile(mockFileName);
  }

  private class TrackerSpy implements Tracker {

    String invokedTrackAction;

    @Override
    public void track(String action) {
      invokedTrackAction = action;
    }
  }
}
