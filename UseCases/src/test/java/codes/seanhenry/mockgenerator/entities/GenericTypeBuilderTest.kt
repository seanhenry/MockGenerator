package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class GenericTypeBuilderTest: TestCase() {

  fun testShouldBuildEmptyGenericType() {
    val type = GenericType.Builder("Type").build()
    assertEquals("Type<>", type.text)
  }

  fun testShouldBuildGenericTypeWithArgument() {
    val type = GenericType.Builder("Type")
        .argument("T")
        .build()
    assertEquals("T", type.arguments[0].text)
    assertEquals("Type<T>", type.text)
  }

  fun testShouldBuildGenericTypeWithArguments() {
    val type = GenericType.Builder("Type")
        .argument().optional { it.type("T") }
        .argument().function { }
        .build()
    assertEquals("T?", type.arguments[0].text)
    assertEquals("() -> ()", type.arguments[1].text)
    assertEquals("Type<T?, () -> ()>", type.text)
  }
}
