package codes.seanhenry.testhelpers;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.intellij.testFramework.PlatformTestCase;
import com.intellij.testFramework.fixtures.CodeInsightTestFixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public abstract class ImportProjectTestCase extends PlatformTestCase {

  private Path testResultPath;
  private CodeInsightTestFixture fixture;

  @Override
  protected void tearDown() throws Exception {
    try {
      fixture.tearDown();
    } catch (Throwable ignored) {
    } finally {
      fixture = null;
      try {
        super.tearDown();
      } catch (Throwable ignored) {}
    }
  }

  @Override
  protected void setUp() throws Exception {
    testResultPath = Files.createTempDirectory("codes.seanhenry.mockgenerator");
    try {
      super.setUp();
    } catch (IllegalStateException ignored) { } 
  }

  @Override
  protected void setUpProject() throws Exception {
    super.setUpProject();
    fixture = ImportProjectTestFixtureFactory.getFixtureFactory().createCodeInsightFixture(getDataPath(), getProjectFileName());
    fixture.setUp();
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
      String actualPath = testResultPath + "/" + actualFileName;
      Files.write(Paths.get(actualPath), getOpenFile().getText().getBytes());
      showDiff(expectedPath, actualPath);
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
