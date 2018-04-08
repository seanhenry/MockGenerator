package codes.seanhenry.mockgenerator.ast

import junit.framework.TestCase

class BracketTypeTest: TestCase() {

  fun testSurroundsTypeWithBrackets() {
    val type = BracketType(TypeIdentifier("Type"))
    assertEquals("(Type)", type.text)
  }
}
