package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PropertyBuilderTest  {

  @Test
  fun testShouldBuildEmptyProperty() {
    val property = Property.Builder("a").build()
    assertEquals("a", property.name)
    assertEquals("", property.type.text)
    assertTrue(property.isWritable)
    assertEquals("var a: ", property.declarationText)
  }

  @Test
  fun testShouldBuildPropertyWithSimpleType() {
    val property = Property.Builder("a")
        .type("String")
        .build()
    assertEquals("a", property.name)
    assertEquals("var a: String", property.declarationText)
  }

  @Test
  fun testShouldBuildPropertyWithAnyType() {
    val property = Property.Builder("a")
        .type().optional { it.type("String") }
        .build()
    assertEquals("a", property.name)
    assertEquals("var a: String?", property.declarationText)
  }

  @Test
  fun testShouldBuildReadonly() {
    val property = Property.Builder("a")
        .readonly()
        .type("String")
        .build()
    assertEquals("a", property.name)
    assertFalse(property.isWritable)
    assertEquals("var a: String", property.declarationText)
  }
}
