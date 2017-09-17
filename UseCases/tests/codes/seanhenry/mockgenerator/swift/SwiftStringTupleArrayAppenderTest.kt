package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase

class SwiftStringTupleArrayAppenderTest : TestCase() {

  fun testShouldWrapValueInCallToAppend() {
    val property = PropertyDeclaration("list", "")
    assertEquals("list.append(value)", SwiftStringTupleArrayAppender().transform(property, "value"))
  }

  fun testShouldWrapAnotherValueInCallToAppend() {
    val property = PropertyDeclaration("anotherList", "")
    assertEquals("anotherList.append(anotherValue)", SwiftStringTupleArrayAppender().transform(property, "anotherValue"))
  }
}
