package codes.seanhenry.error;

import com.intellij.openapi.editor.Editor;

public interface ErrorPresenter {
  void show(String errorMessage, Editor editor);
}
