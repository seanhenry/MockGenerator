package codes.seanhenry.mockgenerator.util;

import junit.framework.TestCase;

public class UniqueMethodNameGeneratorTests extends TestCase {

  private UniqueMethodNameGenerator generator;

  public void test_uniqueMethodName_shouldReturnMethodName() throws Exception {
    assertEquals(new MethodModel[]{
      new MethodModel("methodName")
    }, new String[]{
      "methodName"
    });
  }

  public void test_shouldReturnNull_whenIDDoesNotExist() throws Exception {
    generator = createGenerator();
    assertNull(generator.getMethodName("1"));
  }

  public void test_uniqueMethodName_shouldReturnMethodName_whenNoOverloadedMethods() throws Exception {
    assertEquals(new MethodModel[]{
      new MethodModel("methodName", "param", "param2")
    }, new String[]{
      "methodName" // should not add labels when method name is unique
    });
  }

  public void test_duplicateMethodName_shouldAppendFirstParameterLabel_toMethodName() throws Exception {
    assertEquals(new MethodModel[]{
      new MethodModel("methodName"),
      new MethodModel("anotherMethod"),
      new MethodModel("methodName", "param: Type")
    }, new String[]{
      "methodName",
      "anotherMethod",
      // should add label when method name is overloaded
      // should not use type when label is unique
      "methodNameParam"
    });
  }

  public void test_duplicateMethodName_shouldAppendFirstParameterLabel_toMethodName_whenGivenMethod_hasMultipleParamLabels() throws Exception {
    assertEquals(new MethodModel[]{
      new MethodModel("animate"),
      new MethodModel("animate", "withDuration duration: Type"),
      new MethodModel("animate", "withDuration duration: Type", "delay: Type"),
      new MethodModel("animate", "withDuration duration: Type", "delay: Type", "easing: Ease")
    }, new String[]{
      "animate", // should not add anything when there is nothing to add
      "animateWithDuration", // should only use label unless all other labels are identical
      "animateWithDurationDelay", // should only use labels when last label is unique
      "animateWithDurationDelayEasing"
    });
  }

  public void test_duplicateMethodName_shouldUseTypes_whenLabelsMatch() throws Exception {
    assertEquals(new MethodModel[]{
      new MethodModel("setValue", "_ value: String"),
      new MethodModel("setValue", "_ value: Int"),
      new MethodModel("set", "object: String", "forKey key: String"),
      new MethodModel("set", "object: Int", "forKey key: String"),
      new MethodModel("setNumber", "_ number: Float", "at index: Int"),
      new MethodModel("setNumber", "_ number: Int", "forKey key: String"),
      new MethodModel("setMultiple", "_ number: Int", "for key: Int"),
      new MethodModel("setMultiple", "_ number: Int", "for key: String")
    }, new String[]{
      // should use type when method name and parameters are overloaded
      "setValueString",
      "setValueInt",
      "setObjectStringForKey",
      "setObjectIntForKey",
      "setNumberAt",
      "setNumberForKey",
      "setMultipleIntForInt",
      "setMultipleIntForString",
    });
  }

  public void test_duplicateMethodName_shouldUseTypes_whenLabelsMatch_andNextParamsMatch() throws Exception {
    assertEquals(new MethodModel[]{
      new MethodModel("setValue", "_ value: String"),
      new MethodModel("setValue", "_ value: Int"),
      new MethodModel("setValue", "_ value: String", "forKey key: String"),
      new MethodModel("setValue", "_ value: Int", "forKey key: String")
    }, new String[]{
      // should use type when labels match and there is another identical method except its type
      "setValueString",
      "setValueInt",
      "setValueStringForKey",
      "setValueIntForKey",
    });
  }

  public void test_shouldIgnoreDefaultArguments() throws Exception {
    assertEquals(new MethodModel[]{
      new MethodModel("method", "param: String = \"\""),
      new MethodModel("method", "param: Int = 345")
    }, new String[]{
      "methodParamString",
      "methodParamInt",
    });
  }

  public void test_shouldProcessOneLetterMethodNames() throws Exception {
    assertEquals(new MethodModel[]{
      new MethodModel("a", ""),
      new MethodModel("a", "b")
    }, new String[]{
      "a",
      "aB",
    });
  }

  public void test_shouldAllowDuplicateMethods() throws Exception {
    assertEquals(new MethodModel[]{
      new MethodModel("method"),
      new MethodModel("method")
    }, new String[]{
      "method",
      "method"
    });
  }

  public void test_shouldProcessStrangeWhitespace() throws Exception {
    assertEquals(new MethodModel[]{
      new MethodModel("method", "    param1     :     String   ", " param3   label    : Int  "),
      new MethodModel("method", "param1:String", "param2:Int")
    }, new String[]{
      "methodParam1Param3",
      "methodParam1Param2"
    });
  }

  public void test_shouldProcessIncompleteParameters() throws Exception {
    assertEquals(new MethodModel[]{
      new MethodModel("method", "_"),
      new MethodModel("method", "param1"),
      new MethodModel("method", ":String"),
      new MethodModel("method", ":Int")
    }, new String[]{
      "method",
      "methodParam1",
      "methodString",
      "methodInt"
    });
  }

  private UniqueMethodNameGenerator createGenerator(MethodModel... models) {
    generator = new UniqueMethodNameGenerator(models);
    generator.generateMethodNames();
    return generator;
  }

  private void assertEquals(MethodModel[] models, String[] expected) {
    createGenerator(models);
    for (int i = 0; i < models.length; i++) {
      assertEquals(expected[i], generator.getMethodName(models[i].getID()));
    }
  }
}

