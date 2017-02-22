package codes.seanhenry.util;

import junit.framework.TestCase;

public class PrependStringDecoratorTests extends TestCase {

  public void testShouldPrependPrefixToString() {
    assertPrefixCreatesStringFromString("invoked", "invokedName", "name");
  }

  public void testShouldReturnEmptyString_whenInputIsNull() {
    assertPrefixCreatesStringFromString("invoked", "", null);
  }

  public void testShouldReturnEmptyString_whenInputIsEmpty() {
    assertPrefixCreatesStringFromString("invoked", "", "");
  }

  public void testShouldReturnInput_whenPrefixIsNull() {
    assertPrefixCreatesStringFromString(null, "name", "name");
  }

  public void testShouldReturnInput_whenPrefixIsEmpty() {
    assertPrefixCreatesStringFromString("", "name", "name");
  }

  public void testShouldHandle1LetterName() {
    assertPrefixCreatesStringFromString("invoked", "invokedA", "a");
  }

  private void assertPrefixCreatesStringFromString(String prefix, String expected, String initial) {

    String actual = new PrependStringDecorator(null, prefix).process(initial);
    assertEquals(expected, actual);
  }
}
