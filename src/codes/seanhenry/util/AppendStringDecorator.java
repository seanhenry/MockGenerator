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

public class AppendStringDecorator extends StringDecorator {

  private String suffix;

  public AppendStringDecorator(StringDecorator decorator, String suffix) {
    super(decorator);
    this.suffix = suffix;
  }

  @Override
  public String decorate(String string) {
    if (string == null || string.isEmpty()) return "";
    if (suffix == null || suffix.isEmpty()) return string;
    return string + suffix;
  }
}
