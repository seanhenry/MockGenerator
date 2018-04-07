package codes.seanhenry.mockgenerator.ast
import junit.framework.TestCase

class ArrayTypeBuilderTest: TestCase() {

  fun testShouldBuildEmptyArray() {
    val array = ArrayType.Builder().build()
    assertEquals("[]", array.text)
    assertEquals(Type.EMPTY, array.type)
  }

  fun testShouldBuildArrayWithType() {
    val array = ArrayType.Builder().type("Type").build()
    assertEquals("[Type]", array.text)
    assertEquals("Type", array.type.text)
  }
}

