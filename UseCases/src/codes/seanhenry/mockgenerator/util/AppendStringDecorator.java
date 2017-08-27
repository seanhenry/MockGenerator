package codes.seanhenry.mockgenerator.util;

public class AppendStringDecorator extends StringDecorator {

  private String suffix;

  public AppendStringDecorator(StringDecorator decorator, String suffix) {
    super(decorator);
    this.suffix = suffix;
  }

  @Override
  protected String decorate(String string) {
    if (string == null || string.isEmpty()) return "";
    if (suffix == null || suffix.isEmpty()) return string;
    return string + suffix;
  }
}
