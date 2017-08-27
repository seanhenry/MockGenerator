package codes.seanhenry.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiRecursiveElementVisitor;

import java.util.ArrayList;
import java.util.List;

public class ElementGatheringVisitor<T extends PsiElement> extends PsiRecursiveElementVisitor {

  private Class<T> type;
  private ArrayList<T> elements = new ArrayList<>();

  public ElementGatheringVisitor(Class<T> type) {
    this.type = type;
  }

  @Override
  public void visitElement(PsiElement element) {
    if (type.isInstance(element)) {
      elements.add(type.cast(element));
    }
    super.visitElement(element);
  }

  public List<T> getElements() {
    return elements;
  }
}
