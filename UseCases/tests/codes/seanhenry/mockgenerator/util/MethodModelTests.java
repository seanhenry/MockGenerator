package codes.seanhenry.mockgenerator.util;

import codes.seanhenry.mockgenerator.util.MethodModel;
import junit.framework.TestCase;

public class MethodModelTests extends TestCase {

  private MethodModel model;

  public void test_shouldReturnMethodNameAsFirstPreferredName() {
    assertPreferredName(0, 1, "method");
  }

  public void test_shouldAppendFirstParameterLabelAsSecondPreferredName() {
    assertPreferredName(1, 2, "methodParam0");
  }

  public void test_shouldAppendSecondParameterLabelAsThirdPreferredName() {
    assertPreferredName(2, 3, "methodParam0Param1");
  }

  public void test_shouldAppendFirstType_whenLabelsAreAllUsed() {
    assertPreferredName(1, 3, "methodParam0Type0");
  }

  public void test_shouldAppendSecondType_whenLabelsAreAllUsed() {
    assertPreferredName(2, 5, "methodParam0Type0Param1Type1");
  }

  public void test_shouldBeNull_whenNoMoreLabelsOrTypes() {
    assertNull(0, 2);
    assertNull(1, 4);
    assertNull(2, 6);
  }

  public void test_shouldIgnoreSecondLabelWhenFirstIsWildcard() {
    model = new MethodModel("method", "_ param0: Type0");
    assertEquals("method", model.nextPreferredName());
    assertEquals("methodType0", model.nextPreferredName());
    assertNull(model.nextPreferredName());
  }

  public void test_shouldIgnoreLabelWhenOnlyWildcardLabel() {
    model = new MethodModel("method", "_ : Type0");
    assertEquals("method", model.nextPreferredName());
    assertEquals("methodType0", model.nextPreferredName());
    assertNull(model.nextPreferredName());
  }

  public void test_shouldIgnoreAllWildcardLabels() {
    model = new MethodModel("method", "_ a: Type0", " _ b: Type1");
    assertEquals("method", model.nextPreferredName());
    assertEquals("methodType0", model.nextPreferredName());
    assertEquals("methodType0Type1", model.nextPreferredName());
    assertNull(model.nextPreferredName());
  }

  public void test_shouldOnlyIgnoreWildcardLabels() {
    model = new MethodModel("method", "_ a: Type0", "param1: Type1");
    assertEquals("method", model.nextPreferredName());
    assertEquals("methodParam1", model.nextPreferredName());
    assertEquals("methodType0Param1", model.nextPreferredName());
    assertEquals("methodType0Param1Type1", model.nextPreferredName());
    assertNull(model.nextPreferredName());
  }

  public void test_shouldIgnoreEmptyType() {
    model = new MethodModel("method", "param0 :");
    assertEquals("method", model.nextPreferredName());
    assertEquals("methodParam0", model.nextPreferredName());
    assertNull(model.nextPreferredName());
  }

  public void test_shouldIgnoreEmptyTypeWithNoColon() {
    model = new MethodModel("method", "param0 ");
    assertEquals("method", model.nextPreferredName());
    assertEquals("methodParam0", model.nextPreferredName());
    assertNull(model.nextPreferredName());
  }

  public void test_shouldChooseLabelWhenNoType() {
    model = new MethodModel("method", "label0 name0");
    assertEquals("method", model.nextPreferredName());
    assertEquals("methodLabel0", model.nextPreferredName());
    assertNull(model.nextPreferredName());
  }

  public void test_shouldCreateIDFromLabelsAndTypes() {
    createModel(3);
    assertEquals("method(param0:Type0,param1:Type1,param2:Type2)", model.getID());
  }

  public void test_shouldCreateIDIgnoreWhitespace() {
    model = new MethodModel("method", "   param0   name0  :  Type0   ");
    assertEquals("method(param0:Type0)", model.getID());
  }

  public void test_shouldCreateID_whenNoParams() {
    model = new MethodModel("method");
    assertEquals("method()", model.getID());
  }

  public void test_peekNextShouldBeEqualToNextPreferredName() {
    createModel(1);
    String peeked = model.peekNextPreferredName(); // method
    assertEquals(model.nextPreferredName(), peeked);
    peeked = model.peekNextPreferredName(); // methodParam0
    assertEquals(model.nextPreferredName(), peeked);
    peeked = model.peekNextPreferredName(); // methodParam0Param1
    assertEquals(model.nextPreferredName(), peeked);
    assertNull(model.peekNextPreferredName());
  }

  private void assertPreferredName(int paramCount, int invocationCount, String expected) {
    createModel(paramCount);
    assertEquals(expected, nextPreferredName(invocationCount));
  }

  private void assertNull(int paramCount, int invocationCount) {
    createModel(paramCount);
    assertNull(nextPreferredName(invocationCount));
  }

  private void createModel(int parameterCount) {
    String[] parameters = new String[parameterCount];
    for (int i = 0; i < parameterCount; i++) {
      parameters[i] = "param" + i + ": Type" + i;
    }
    model = new MethodModel("method", parameters);
  }

  private String nextPreferredName(int count) {
    for (int i = 0; i < count - 1; i++) {
      model.nextPreferredName();
    }
    return model.nextPreferredName();
  }
}
