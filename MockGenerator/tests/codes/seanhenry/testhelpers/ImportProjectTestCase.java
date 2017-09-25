package codes.seanhenry.testhelpers;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.impl.VirtualFilePointerTracker;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.PlatformTestCase;
import com.intellij.testFramework.fixtures.CodeInsightTestFixture;

public abstract class ImportProjectTestCase extends PlatformTestCase {

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
}