package codes.seanhenry.intentions;

import codes.seanhenry.helpers.*;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.*;
import com.intellij.testFramework.fixtures.*;
import com.intellij.testFramework.fixtures.impl.CodeInsightTestFixtureImpl;
import com.intellij.testFramework.fixtures.impl.TempDirTestFixtureImpl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MockGeneratingIntentionTests extends PlatformTestCase {

  private final String dataPath = "/Users/sean/source/plugins/community/MockGenerator/testData/TestProject";
  private CodeInsightTestFixture myFixture;

  @Override
  protected void tearDown() throws Exception {
    try {
      myFixture.tearDown();
    } finally {
      myFixture = null;
      super.tearDown();
    }
  }

  @Override
  protected void setUpProject() throws Exception {
    super.setUpProject();

    TempDirTestFixtureImpl tempDirTestFixture = new TempDirTestFixtureImpl();
    String projectDir = tempDirTestFixture.getTempDirPath();
    copyFolder(new File(dataPath), new File(projectDir));

    String name = getClass().getName() + "." + getName();
    myFixture = new CodeInsightTestFixtureImpl(new MyHeavyIdeaTestFixtureImpl(name, projectDir + "/TestProject.xcodeproj"), tempDirTestFixture);

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
      "UIKitProtocol",
    };

    for (String fileName : fileNames) {
      runTest(fileName);
    }
  }

  private void runTest(String fileName) throws IOException {
    String expectedFileName = fileName + "Mock_expected.swift";
    String mockFileName = fileName + "Mock.swift";
    System.out.println("Running test for " + fileName);
    PsiFile[] files = FilenameIndex.getFilesByName(getActiveProject(), mockFileName, GlobalSearchScope.projectScope(getActiveProject()));
    PsiFile psiFile = files[0];
    VirtualFile file = psiFile.getVirtualFile();
    myFixture.configureFromExistingVirtualFile(file);

    IntentionAction action = myFixture.findSingleIntention("Generate mock");

    WriteCommandAction.runWriteCommandAction(getActiveProject(), () -> action.invoke(getActiveProject(), myFixture.getEditor(), psiFile));
    myFixture.checkResultByFile(expectedFileName, true);
  }

  private void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
    //Check if sourceFolder is a directory or file
    //If sourceFolder is file; then copy the file directly to new location
    if (sourceFolder.isDirectory())
    {
      //Verify if destinationFolder is already present; If not then create it
      if (!destinationFolder.exists())
      {
        destinationFolder.mkdir();
        //System.out.println("Directory created :: " + destinationFolder);
      }

      //Get all files from source directory
      String files[] = sourceFolder.list();

      //Iterate over all files and copy them to destinationFolder one by one
      for (String file : files)
      {
        File srcFile = new File(sourceFolder, file);
        File destFile = new File(destinationFolder, file);

        //Recursive function call
        copyFolder(srcFile, destFile);
      }
    }
    else
    {
      //Copy the file content from one place to another
      Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
      //System.out.println("File copied :: " + destinationFolder);
    }
  }
}
