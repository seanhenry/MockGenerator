package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class ProtocolPropertyTest: TestCase() {

  fun testTrimsGetSetFromSignature() {
    val property = ProtocolProperty("", "", false, "var prop: Type{ get set }")
    assertEquals("var prop: Type", property.getTrimmedSignature())
  }

  fun testTrimsGetSet_andWhitespaceFromSignature() {
    val property = ProtocolProperty("", "", false, "var prop: Type    { get set }")
    assertEquals("var prop: Type", property.getTrimmedSignature())
  }

  fun testTrimsWhitespace_whenNoGetSetClause() {
    val property = ProtocolProperty("", "", false, "var prop: Type    ")
    assertEquals("var prop: Type", property.getTrimmedSignature())
  }

  fun testTrimsWhitespaceAndNewlinesAndTabs() {
    val property = ProtocolProperty("", "", false, "var prop: Type \t\n {\n get set \n}   ")
    assertEquals("var prop: Type", property.getTrimmedSignature())
  }
}
