package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PropertyTest {

  @Test
  fun testTrimsGetSetFromSignature() {
    val property = Property("", TypeIdentifier.EMPTY, false, "   var prop: Type{ get set }")
    assertEquals("var prop: Type", property.getTrimmedDeclarationText())
  }

  @Test
  fun testTrimsGetSet_andWhitespaceFromSignature() {
    val property = Property("", TypeIdentifier.EMPTY, false, "   var prop: Type    { get set }")
    assertEquals("var prop: Type", property.getTrimmedDeclarationText())
  }

  @Test
  fun testTrimsWhitespace_whenNoGetSetClause() {
    val property = Property("", TypeIdentifier.EMPTY, false,"   var prop: Type    ")
    assertEquals("var prop: Type", property.getTrimmedDeclarationText())
  }

  @Test
  fun testTrimsWhitespaceAndNewlinesAndTabs() {
    val property = Property("", TypeIdentifier.EMPTY, false, " \n\tvar prop: Type \t\n {\n get set \n}   ")
    assertEquals("var prop: Type", property.getTrimmedDeclarationText())
  }
}
