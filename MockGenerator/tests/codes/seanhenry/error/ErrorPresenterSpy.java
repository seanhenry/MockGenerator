package codes.seanhenry.error;

import com.intellij.openapi.editor.Editor;

public class ErrorPresenterSpy implements ErrorPresenter {

  public String invokedErrorMessage;
  @Override
  public void show(String errorMessage, Editor editor) {
    invokedErrorMessage = errorMessage;
  }
}
