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
