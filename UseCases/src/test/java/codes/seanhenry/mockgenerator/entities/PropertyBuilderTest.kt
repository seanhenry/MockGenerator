package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class PropertyBuilderTest : TestCase() {

  fun testShouldBuildEmptyProperty() {
    val property = Property.Builder("a").build()
    assertEquals("a", property.name)
    assertEquals("", property.type.text)
    assertTrue(property.isWritable)
    assertEquals("var a: ", property.declarationText)
  }

  fun testShouldBuildPropertyWithSimpleType() {
    val property = Property.Builder("a")
        .type("String")
        .build()
    assertEquals("a", property.name)
    assertEquals("var a: String", property.declarationText)
  }

  fun testShouldBuildPropertyWithAnyType() {
    val property = Property.Builder("a")
        .type().optional { it.type("String") }
        .build()
    assertEquals("a", property.name)
    assertEquals("var a: String?", property.declarationText)
  }

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
