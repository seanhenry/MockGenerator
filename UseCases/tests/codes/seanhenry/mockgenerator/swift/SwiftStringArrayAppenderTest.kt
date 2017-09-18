package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase

class SwiftStringArrayAppenderTest : TestCase() {

  fun testShouldWrapValueInCallToAppend() {
    val property = PropertyDeclaration("list", "")
    assertEquals("list.append(value)", SwiftStringArrayAppender().transform(property, "value"))
  }

  fun testShouldWrapAnotherValueInCallToAppend() {
    val property = PropertyDeclaration("anotherList", "")
    assertEquals("anotherList.append(anotherValue)", SwiftStringArrayAppender().transform(property, "anotherValue"))
  }
}
