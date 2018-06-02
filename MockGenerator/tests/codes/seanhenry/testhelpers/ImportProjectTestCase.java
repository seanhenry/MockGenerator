package codes.seanhenry.testhelpers;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.idea.IdeaTestApplication;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.openapi.vfs.newvfs.persistent.PersistentFS;
import com.intellij.openapi.vfs.newvfs.persistent.PersistentFSImpl;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.testFramework.fixtures.CodeInsightTestFixture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class ImportProjectTestCase extends UsefulTestCase {

  private Path testResultPath;
  private CodeInsightTestFixture fixture;

  @Override
  protected void tearDown() throws Exception {
      fixture.tearDown();
      fixture = null;
      super.tearDown();
  }

  @Override
  protected void setUp() throws Exception {
    allowAccessToXcodeDirectory();
    testResultPath = Files.createTempDirectory("codes.seanhenry.mockgenerator");
    super.setUp();
    this.initApplication();
    fixture = ImportProjectTestFixtureFactory.getFixtureFactory().createCodeInsightFixture(getDataPath(), getProjectFileName());
    fixture.setUp();
  }

  private void initApplication() throws Exception {
     IdeaTestApplication.getInstance(null);
        ((PersistentFSImpl)PersistentFS.getInstance()).cleanPersistedContents();
  }

  private void allowAccessToXcodeDirectory() throws IOException {
    String xcodePath = findXcodePath();
    VfsRootAccess.allowRootAccess(xcodePath);
  }

  private String findXcodePath() throws IOException {
    InputStream result = Runtime.getRuntime().exec("xcode-select -p").getInputStream();
    BufferedReader input = new BufferedReader(new
        InputStreamReader(result));
    return input.lines().collect(Collectors.joining());
  }

  protected abstract String getDataPath();
  protected abstract String getProjectFileName();

  protected void invokeIntention(String intentionName, PsiFile file) {
    IntentionAction action = fixture.findSingleIntention(intentionName);
    WriteCommandAction.runWriteCommandAction(getFixtureProject(), () -> action.invoke(getFixtureProject(), fixture.getEditor(), file));
  }

  protected Project getFixtureProject() {
    return getFixture().getProject();
  }

  protected CodeInsightTestFixture getFixture() {
    return fixture;
  }

  protected void assertFilesEqual(String expectedFileName, String actualFileName) throws IOException {
    String expectedPath = getDataPath() + "/" + expectedFileName;
    byte[] expectedBytes = Files.readAllBytes(Paths.get(expectedPath));
    byte[] actualBytes = getOpenFile().getText().getBytes();
    if (!Arrays.equals(actualBytes, expectedBytes)) {
      Path resultDirectory = Paths.get("test_results");
      if (!Files.isDirectory(resultDirectory)) {
        Files.createDirectory(resultDirectory);
      }
      String actualFilePath = "test_results/" + actualFileName;
      Files.write(Paths.get(actualFilePath), getOpenFile().getText().getBytes());
      showDiff(expectedPath, actualFilePath);
      fail();
    }
  }

  private PsiFile getOpenFile() {
    return InjectedLanguageUtil.getTopLevelFile(getFixture().getFile());
  }

  private void showDiff(String expectedPath, String actualPath) throws IOException {
    Runtime.getRuntime().exec("/usr/bin/opendiff " + actualPath + " " + expectedPath);
  }
}
