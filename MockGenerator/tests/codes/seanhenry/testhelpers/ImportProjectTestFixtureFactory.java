package codes.seanhenry.testhelpers;

import com.intellij.testFramework.fixtures.CodeInsightTestFixture;

public abstract class ImportProjectTestFixtureFactory {

  public abstract CodeInsightTestFixture createCodeInsightFixture(String dataPath, String projectFileName);

  public static ImportProjectTestFixtureFactory getFixtureFactory() {
    return new ImportProjectTestFixtureFactoryImpl();
  }
}
