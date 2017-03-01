package codes.seanhenry.util;

import junit.framework.TestCase;

public class UniqueMethodNameGeneratorTests extends TestCase {

  private UniqueMethodNameGenerator generator;

  public void test_uniqueMethodName_shouldReturnMethodName() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1","methodName")
    );
    assertEquals("methodName", generator.generate("1"));
  }

  public void test_shouldReturnNull_whenIDDoesNotExist() throws Exception {
    generator = new UniqueMethodNameGenerator();
    assertNull(generator.generate("1"));
  }

  public void test_uniqueMethodName_shouldReturnMethodName_whenNoOverloadedMethods() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "methodName", "param", "param2")
    );
    // should not add labels when method name is unique
    assertEquals("methodName", generator.generate("1"));
  }

  public void test_duplicateMethodName_shouldAppendFirstParameterLabel_toMethodName() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "methodName"),
      new UniqueMethodNameGenerator.MethodModel("2", "anotherMethod"),
      new UniqueMethodNameGenerator.MethodModel("3", "methodName", "param: Type")
    );
    assertEquals("methodName", generator.generate("1"));
    assertEquals("anotherMethod", generator.generate("2"));
    // should add label when method name is overloaded
    // should not use type when label is unique
    assertEquals("methodNameParam", generator.generate("3"));
  }

  public void test_duplicateMethodName_shouldAppendFirstParameterLabel_toMethodName_whenGivenMethod_hasMultipleParamLabels() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "animate"),
      new UniqueMethodNameGenerator.MethodModel("2", "animate", "withDuration duration: Type"),
      new UniqueMethodNameGenerator.MethodModel("3", "animate", "withDuration duration: Type", "delay: Type"),
      new UniqueMethodNameGenerator.MethodModel("4", "animate", "withDuration duration: Type", "delay: Type", "easing: Ease")
    );
    // should not add anything when there is nothing to add
    assertEquals("animate", generator.generate("1"));
    // should only use label unless all other labels are identical
    assertEquals("animateWithDuration", generator.generate("2"));
    // should only use labels when last label is unique
    assertEquals("animateWithDurationDelay", generator.generate("3"));
    assertEquals("animateWithDurationDelayEasing", generator.generate("4"));
  }

  public void test_duplicateMethodName_shouldUseTypes_whenLabelsMatch() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "setValue", "_ value: String"),
      new UniqueMethodNameGenerator.MethodModel("2", "setValue", "_ value: Int"),
      new UniqueMethodNameGenerator.MethodModel("3", "set", "object: String", "forKey key: String"),
      new UniqueMethodNameGenerator.MethodModel("4", "set", "object: Int", "forKey key: String"),
      new UniqueMethodNameGenerator.MethodModel("5", "setNumber", "_ number: Float", "at index: Int"),
      new UniqueMethodNameGenerator.MethodModel("6", "setNumber", "_ number: Int", "forKey key: String"),
      new UniqueMethodNameGenerator.MethodModel("7", "setMultiple", "_ number: Int", "for key: Int"),
      new UniqueMethodNameGenerator.MethodModel("8", "setMultiple", "_ number: Int", "for key: String")
    );
    // should use type when method name and parameters are overloaded
    assertEquals("setValueString", generator.generate("1"));
    assertEquals("setValueInt", generator.generate("2"));
    assertEquals("setObjectStringForKey", generator.generate("3"));
    assertEquals("setObjectIntForKey", generator.generate("4"));
    assertEquals("setNumberAt", generator.generate("5"));
    assertEquals("setNumberForKey", generator.generate("6"));
    assertEquals("setMultipleIntForInt", generator.generate("7"));
    assertEquals("setMultipleIntForString", generator.generate("8"));
  }

  public void test_duplicateMethodName_shouldUseTypes_whenLabelsMatch_andNextParamsMatch() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "setValue", "_ value: String"),
      new UniqueMethodNameGenerator.MethodModel("2", "setValue", "_ value: Int"),
      new UniqueMethodNameGenerator.MethodModel("3", "setValue", "_ value: String", "forKey key: String"),
      new UniqueMethodNameGenerator.MethodModel("4", "setValue", "_ value: Int", "forKey key: String")
    );
    // should use type when labels match and there is another identical method except its type
    assertEquals("setValueString", generator.generate("1"));
    assertEquals("setValueInt", generator.generate("2"));
    assertEquals("setValueStringForKey", generator.generate("3"));
    assertEquals("setValueIntForKey", generator.generate("4"));
  }

  public void test_shouldIgnoreDefaultArguments() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "method", "param: String = \"\""),
      new UniqueMethodNameGenerator.MethodModel("2", "method", "param: Int = 345")
    );
    assertEquals("methodParamString", generator.generate("1"));
    assertEquals("methodParamInt", generator.generate("2"));
  }

  public void test_shouldProcessOneLetterMethodNames() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "a", ""),
      new UniqueMethodNameGenerator.MethodModel("2", "a", "b")
    );
    assertEquals("a", generator.generate("1"));
    assertEquals("aB", generator.generate("2"));
  }

  public void test_shouldAllowDuplicateMethods() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "method"),
      new UniqueMethodNameGenerator.MethodModel("2", "method")
    );
    assertEquals("method", generator.generate("1"));
    assertEquals("method", generator.generate("2"));
  }

  public void test_shouldProcessStrangeWhitespace() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "method", "    param1     :     String   ", " param3   label    : Int  "),
      new UniqueMethodNameGenerator.MethodModel("2", "method", "param1:String", "param2:Int")
    );
    assertEquals("methodParam1Param3", generator.generate("1"));
    assertEquals("methodParam1Param2", generator.generate("2"));
  }

  public void test_shouldProcessIncompleteParameters() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "method", "_"),
      new UniqueMethodNameGenerator.MethodModel("2", "method", "param1"),
      new UniqueMethodNameGenerator.MethodModel("3", "method", ":String"),
      new UniqueMethodNameGenerator.MethodModel("4", "method", ":Int")
    );
    assertEquals("method", generator.generate("1"));
    assertEquals("methodParam1", generator.generate("2"));
    assertEquals("methodString", generator.generate("3"));
    assertEquals("methodInt", generator.generate("4"));
  }
}
