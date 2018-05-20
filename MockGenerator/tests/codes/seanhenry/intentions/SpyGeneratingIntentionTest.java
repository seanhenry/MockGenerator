package codes.seanhenry.intentions;

import codes.seanhenry.analytics.Tracker;
import codes.seanhenry.error.ErrorPresenterSpy;
import codes.seanhenry.testhelpers.ImportProjectTestCase;
import com.google.common.collect.Lists;
import com.intellij.psi.PsiFile;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;

public class SpyGeneratingIntentionTest extends ImportProjectTestCase {

  private ErrorPresenterSpy errorPresenterSpy;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    errorPresenterSpy = new ErrorPresenterSpy();
    BaseGeneratingIntention.errorPresenter = errorPresenterSpy;
    BaseGeneratingIntention.tracker = new TrackerSpy();
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
    List<Pair<String, String>> fileErrors = Lists.newArrayList();
    fileErrors.add(new Pair<>("NoInheritanceError", "Mock class does not inherit from anything."));
    fileErrors.add(new Pair<>("NothingResolvedError", "NothingHere could not be resolved."));
    fileErrors.add(new Pair<>("MultiNothingResolvedError", "NothingHere, OrHere could not be resolved."));
    fileErrors.add(new Pair<>("InheritUnexpectedTypeError", null));
    for (Pair<String, String> fileError: fileErrors) {
      errorPresenterSpy.invokedErrorMessage = null;
      runErrorTest(fileError.getFirst());
      assertEquals(fileError.getSecond(), errorPresenterSpy.invokedErrorMessage);
    }

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
      "TupleProtocol",

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
      "PrivateInitializerClass",
      "MultipleVariable",

      "Delete",
      "ClassAndProtocol",
    };

    for (String fileName : fileNames) {
      runTest(fileName);
    }
    Assert.assertFalse(isIntentionAvailable("NotAvailableInProductionCodeTarget"));
    Assert.assertFalse(isIntentionAvailable("ClassNotFoundError"));
    runSuccessAnalyticsTest();
  }

  private void runSuccessAnalyticsTest() throws Exception {
    TrackerSpy trackerSpy = new TrackerSpy();
    BaseGeneratingIntention.tracker = trackerSpy;
    runTest("SimpleProtocol");
    Assert.assertEquals("generated", trackerSpy.invokedTrackAction);
  }

  private boolean isIntentionAvailable(String fileName) {
    configureFile(fileName + ".swift");
    return getFixture().filterAvailableIntentions("Generate spy").size() > 0;
  }

  private void runTest(String fileName) throws IOException {
    String expectedFileName = fileName + "Mock_expected.swift";
    String mockFileName = fileName + "Mock.swift";
    System.out.println("Running test for " + fileName);
    PsiFile targetFile = configureFile(mockFileName);
    invokeIntention("Generate spy", targetFile);
    assertFilesEqual(expectedFileName, mockFileName);
  }

  @NotNull
  private PsiFile configureFile(String mockFileName) {
    return getFixture().configureByFile(mockFileName);
  }

  private void runErrorTest(String fileName) {
    String mockFileName = fileName + ".swift";
    PsiFile targetFile = configureFile(mockFileName);
    invokeIntention("Generate spy", targetFile);
  }

  private class TrackerSpy implements Tracker {

    String invokedTrackAction;

    @Override
    public void track(String action) {
      invokedTrackAction = action;
    }
  }
}
