package codes.seanhenry.mockgenerator.util;

public class DefaultValueStore extends Store {

  public String getDefaultValue(String typeName) {
    String trimmed = typeName.replaceAll("\\s", "");
    if (OptionalUtil.isOptional(trimmed)) {
      return null;
    } else if (isArray(trimmed)) {
      return "[]";
    } else if (isDictionary(trimmed)) {
      return "[:]";
    }
    return getProperties().getProperty(trimmed, null);
  }

  private boolean isArray(String typeName) {
    return typeName.matches("\\[[\\w0-9]+\\]");
  }

  private boolean isDictionary(String typeName) {
    return typeName.matches("\\[[\\w0-9]+:[\\w0-9]+\\]");
  }

  @Override
  protected String getPropertiesFileName() {
    return "defaultValues.properties";
  }
}
