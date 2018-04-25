package codes.seanhenry.testhelpers;

import com.intellij.ide.impl.ProjectUtil;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.testFramework.fixtures.IdeaProjectTestFixture;
import com.intellij.testFramework.fixtures.TempDirTestFixture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ImportProjectTestFixture implements IdeaProjectTestFixture {

  private final String projectDirectoryPath;
  private final String projectFileName;
  private final TempDirTestFixture tempDirTestFixture;
  private Project project;

  public ImportProjectTestFixture(String projectDirectoryPath, String projectFileName, TempDirTestFixture tempDirTestFixture) {
    this.projectFileName = projectFileName;
    this.tempDirTestFixture = tempDirTestFixture;
    this.projectDirectoryPath = projectDirectoryPath;
  }

  @Override
  public void setUp() throws Exception {
    String tempDirectory = tempDirTestFixture.getTempDirPath();
    copyFolder(new File(projectDirectoryPath), new File(tempDirectory));
    project = ProjectUtil.openOrImport(tempDirectory + "/" + projectFileName, null, true);
    ProjectManagerEx.getInstanceEx().openTestProject(getProject());
  }

  @Override
  public void tearDown() throws Exception {
    if (getProject() != null) {
      ProjectManagerEx.getInstanceEx().closeTestProject(getProject());
      ProjectUtil.closeAndDispose(getProject());
      project = null;
    }
  }

  @Override
  public Project getProject() {
    return project;
  }

  @Override
  public Module getModule() {
    return null;
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
