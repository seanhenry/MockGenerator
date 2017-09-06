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

  abstract protected String decorate(String string);
}
