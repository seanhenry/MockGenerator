package codes.seanhenry.mockgenerator.util;

import junit.framework.TestCase;

public class StringDecoratorTests extends TestCase {

  class EmptyStringDecorator extends StringDecorator {

    public EmptyStringDecorator(StringDecorator decorator) {
      super(decorator);
    }

    @Override
    protected String decorate(String string) {
      return string;
    }
  }

  public void testShouldProcessStringInDecorator() {
    PrependStringDecorator decorator = new PrependStringDecorator(null, "prefix");
    StringDecorator stringDecorator = new EmptyStringDecorator(decorator);
    assertEquals("prefixName", stringDecorator.process("name"));
  }

  public void testShouldNotGiveStringToNullDecorator() {
    StringDecorator stringDecorator = new EmptyStringDecorator(null);
    assertEquals("name", stringDecorator.process("name"));
  }

  public void testShouldProcessStringInMultipleDecorators() {
    AppendStringDecorator appendDecorator = new AppendStringDecorator(null, "Suffix");
    PrependStringDecorator prependDecorator = new PrependStringDecorator(appendDecorator, "prefix");
    StringDecorator stringDecorator = new EmptyStringDecorator(prependDecorator);
    assertEquals("prefixNameSuffix", stringDecorator.process("name"));
  }
}
