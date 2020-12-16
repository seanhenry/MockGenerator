package codes.seanhenry.error;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.editor.Editor;

public class DefaultErrorPresenter implements ErrorPresenter {

  @Override
  public void show(String errorMessage, Editor editor) {
    HintManager.getInstance().showErrorHint(editor, errorMessage);
  }
}
