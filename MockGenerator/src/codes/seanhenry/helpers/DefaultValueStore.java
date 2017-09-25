package codes.seanhenry.helpers;

import codes.seanhenry.util.MySwiftPsiUtil;
import com.jetbrains.swift.psi.SwiftArrayDictionaryTypeElement;
import com.jetbrains.swift.psi.SwiftReferenceTypeElement;
import com.jetbrains.swift.psi.SwiftTypeElement;

public class DefaultValueStore extends Store {

  public String getDefaultValue(SwiftTypeElement element) {
    if (element == null || MySwiftPsiUtil.isOptional(element)) {
      return null;
    }
    return getProperties().getProperty(getClassName(element), "");
  }

  private static String getClassName(SwiftTypeElement element) {
    String className = element.getText();
    if (element instanceof SwiftReferenceTypeElement) {
      className = ((SwiftReferenceTypeElement)element).getName();
    } else if (element instanceof SwiftArrayDictionaryTypeElement) {
      SwiftArrayDictionaryTypeElement arrayOrDictionary = (SwiftArrayDictionaryTypeElement)element;
      className = arrayOrDictionary.isDictionary() ? "Dictionary" : "Array";
    }
    return className;
  }

  @Override
  protected String getPropertiesFileName() {
    return "defaultValues.properties";
  }
}
