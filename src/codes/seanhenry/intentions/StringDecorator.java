package codes.seanhenry.intentions;

public abstract class StringDecorator {

  private StringDecorator decorator;

  public StringDecorator(StringDecorator decorator) {
    this.decorator = decorator;
  }

  public final String process(String string) {
    String decorated = decorate(string);
    if (decorator != null) {
      return decorator.process(decorated);
    }
    return decorated;
  }

  abstract public String decorate(String string);
}
