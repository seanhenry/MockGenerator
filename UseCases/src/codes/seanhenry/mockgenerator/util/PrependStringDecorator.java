package codes.seanhenry.mockgenerator.util;

public class PrependStringDecorator extends StringDecorator {

  private String prefix;

  public PrependStringDecorator(StringDecorator decorator, String prefix) {
    super(decorator);
    this.prefix = prefix;
  }

  @Override
  protected String decorate(String string) {
    if (string == null || string.isEmpty()) return "";
    if (prefix == null || prefix.isEmpty()) return string;
    String capitalised = string.substring(0, 1).toUpperCase();
    if (1 < string.length())
        capitalised += string.substring(1);
    return prefix + capitalised;
  }
}
