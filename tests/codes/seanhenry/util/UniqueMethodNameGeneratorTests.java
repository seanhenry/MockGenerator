/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package codes.seanhenry.util;

import junit.framework.TestCase;

public class UniqueMethodNameGeneratorTests extends TestCase {

  private UniqueMethodNameGenerator generator;

  public void test_uniqueMethodName_shouldReturnFirstPartOfMethod() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1","methodName")
    );
    assertEquals("methodName", generator.generate("1"));
  }

  public void test_duplicateMethodName_shouldAppendFirstParameterLabel_toMethodName() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "methodName"),
      new UniqueMethodNameGenerator.MethodModel("2", "anotherMethod"),
      new UniqueMethodNameGenerator.MethodModel("3", "methodName", "param")
    );
    assertEquals("methodName", generator.generate("1"));
  }

  public void test_duplicateMethodName_shouldAppendFirstParameterLabel_toMethodName_whenGivenMethod_hasParamLabel() throws Exception {
    generator = new UniqueMethodNameGenerator(
      new UniqueMethodNameGenerator.MethodModel("1", "methodName"),
      new UniqueMethodNameGenerator.MethodModel("2", "anotherMethod"),
      new UniqueMethodNameGenerator.MethodModel("3", "methodName", "param")
    );
    assertEquals("methodNameParam", generator.generate("3"));
  }
}
