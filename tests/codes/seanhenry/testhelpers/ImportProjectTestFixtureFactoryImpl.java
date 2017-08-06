package codes.seanhenry.testhelpers;

import com.intellij.testFramework.fixtures.CodeInsightTestFixture;
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory;
import com.intellij.testFramework.fixtures.impl.TempDirTestFixtureImpl;

public class ImportProjectTestFixtureFactoryImpl extends ImportProjectTestFixtureFactory {

  @Override
  public CodeInsightTestFixture createCodeInsightFixture(String dataPath, String projectFileName) {
    TempDirTestFixtureImpl tempDirTestFixture = new TempDirTestFixtureImpl();
    CodeInsightTestFixture fixture = IdeaTestFixtureFactory
      .getFixtureFactory()
      .createCodeInsightFixture(new ImportProjectTestFixture(dataPath, projectFileName, tempDirTestFixture), tempDirTestFixture);
    fixture.setTestDataPath(dataPath);
    return fixture;
  }
}
