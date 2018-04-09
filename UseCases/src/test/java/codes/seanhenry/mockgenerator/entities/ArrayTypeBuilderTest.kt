package codes.seanhenry.mockgenerator.entities
import junit.framework.TestCase

class ArrayTypeBuilderTest: TestCase() {

  fun testShouldBuildEmptyArray() {
    val array = ArrayType.Builder().build()
    assertEquals("[]", array.text)
    assertEquals(TypeIdentifier.EMPTY, array.type)
  }

  fun testShouldBuildArrayWithType() {
    val array = ArrayType.Builder().type("Type").build()
    assertEquals("[Type]", array.text)
    assertEquals("Type", array.type.text)
  }

  fun testShouldBuildArrayWithAnyType() {
    val array = ArrayType.Builder().type().array { it.type("Type") }.build()
    assertEquals("[[Type]]", array.text)
    assertEquals("[Type]", array.type.text)
  }

  fun testShouldBuildVerboseArray() {
    val array = ArrayType.Builder()
        .verbose()
        .type("Type")
        .build()
    assertEquals("Array<Type>", array.text)
    assertEquals("Type", array.type.text)
  }
}

