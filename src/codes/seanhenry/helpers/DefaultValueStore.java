package codes.seanhenry.helpers;

import codes.seanhenry.util.MySwiftPsiUtil;
import com.jetbrains.swift.psi.SwiftArrayDictionaryTypeElement;
import com.jetbrains.swift.psi.SwiftReferenceTypeElement;
import com.jetbrains.swift.psi.SwiftTypeElement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DefaultValueStore {

  private Properties defaultValues;

  public String getDefaultValue(SwiftTypeElement element) {
    if (element == null || MySwiftPsiUtil.isOptional(element)) {
      return null;
    }
    return getDefaultValues().getProperty(getClassName(element), "");
  }

  private String getClassName(SwiftTypeElement element) {
    String className = element.getText();
    if (element instanceof SwiftReferenceTypeElement) {
      className = ((SwiftReferenceTypeElement)element).getName();
    } else if (element instanceof SwiftArrayDictionaryTypeElement) {
      SwiftArrayDictionaryTypeElement arrayOrDictionary = (SwiftArrayDictionaryTypeElement)element;
      className = arrayOrDictionary.isDictionary() ? "Dictionary" : "Array";
    }
    return className;
  }

  private Properties getDefaultValues() {
    if (defaultValues != null) {
      return defaultValues;
    }
    try {
      defaultValues = createDefaultValues();
    } catch (IOException ignored) {}
    return defaultValues;
  }

  private Properties createDefaultValues() throws IOException {
    Properties defaultValues = new Properties();
    InputStream in = getClass().getResourceAsStream("defaultValues.properties");
    try {
      defaultValues.load(in);
    } catch (Throwable ignored) { }
    finally {
      in.close();
    }
    return defaultValues;
  }
}
