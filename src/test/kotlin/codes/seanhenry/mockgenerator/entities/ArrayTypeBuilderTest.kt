package codes.seanhenry.mockgenerator.entities
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ArrayTypeBuilderTest {

  @Test
  fun testShouldBuildEmptyArray() {
    val array = ArrayType.Builder().build()
    assertEquals("[]", array.text)
    assertEquals(TypeIdentifier.EMPTY, array.type)
  }

  @Test
  fun testShouldBuildArrayWithType() {
    val array = ArrayType.Builder().type("Type").build()
    assertEquals("[Type]", array.text)
    assertEquals("Type", array.type.text)
  }

  @Test
  fun testShouldBuildArrayWithAnyType() {
    val array = ArrayType.Builder().type().array { it.type("Type") }.build()
    assertEquals("[[Type]]", array.text)
    assertEquals("[Type]", array.type.text)
  }

  @Test
  fun testShouldBuildVerboseArray() {
    val array = ArrayType.Builder()
        .verbose()
        .type("Type")
        .build()
    assertEquals("Array<Type>", array.text)
    assertEquals("Type", array.type.text)
  }
}

