package codes.seanhenry.mockgenerator.util;

import junit.framework.TestCase;

public class AppendStringDecoratorTests extends TestCase {

  public void testShouldAppendSuffixToString() {
    assertSuffixCreatesStringFromString("WasCalled", "nameWasCalled", "name");
  }

  public void testShouldReturnEmptyString_whenInputIsNull() {
    assertSuffixCreatesStringFromString("invoked", "", null);
  }

  public void testShouldReturnEmptyString_whenInputIsEmpty() {
    assertSuffixCreatesStringFromString("invoked", "", "");
  }

  public void testShouldReturnInput_whenSuffixIsNull() {
    assertSuffixCreatesStringFromString(null, "name", "name");
  }

  public void testShouldReturnInput_whenSuffixIsEmpty() {
    assertSuffixCreatesStringFromString("", "name", "name");
  }

  public void testShouldHandle1LetterName() {
    assertSuffixCreatesStringFromString("WasCalled", "aWasCalled", "a");
  }

  private void assertSuffixCreatesStringFromString(String suffix, String expected, String initial) {
    String actual = new AppendStringDecorator(null, suffix).decorate(initial);
    assertEquals(expected, actual);
  }
}
