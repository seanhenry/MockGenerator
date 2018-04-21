package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class PropertyTest: TestCase() {

  fun testTrimsGetSetFromSignature() {
    val property = Property("", TypeIdentifier.EMPTY, false, "var prop: Type{ get set }")
    assertEquals("var prop: Type", property.getTrimmedDeclarationText())
  }

  fun testTrimsGetSet_andWhitespaceFromSignature() {
    val property = Property("", TypeIdentifier.EMPTY, false, "var prop: Type    { get set }")
    assertEquals("var prop: Type", property.getTrimmedDeclarationText())
  }

  fun testTrimsWhitespace_whenNoGetSetClause() {
    val property = Property("", TypeIdentifier.EMPTY, false, "var prop: Type    ")
    assertEquals("var prop: Type", property.getTrimmedDeclarationText())
  }

  fun testTrimsWhitespaceAndNewlinesAndTabs() {
    val property = Property("", TypeIdentifier.EMPTY, false, "var prop: Type \t\n {\n get set \n}   ")
    assertEquals("var prop: Type", property.getTrimmedDeclarationText())
  }
}
