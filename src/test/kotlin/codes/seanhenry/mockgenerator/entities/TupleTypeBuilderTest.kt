package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TupleTypeBuilderTest  {

  @Test
  fun testShouldBuildEmptyTuple() {
    val tuple = TupleType.Builder().build()
    assertEquals("()", tuple.text)
    assertTrue(tuple.types.isEmpty())
  }

  @Test
  fun testShouldBuildSimpleTypeTuple() {
    val tuple = TupleType.Builder()
        .element("Int")
        .build()
    assertEquals("(Int)", tuple.text)
    assertEquals("Int", tuple.types[0].text)
  }

  @Test
  fun testShouldBuildComplexTypeTuple() {
    val tuple = TupleType.Builder()
        .element().optional { it.type("A") }
        .element().array { it.type("B") }
        .build()
    assertEquals("(A?, [B])", tuple.text)
    assertEquals("A?", tuple.types[0].text)
    assertEquals("[B]", tuple.types[1].text)
  }

  @Test
  fun testShouldBuildAlreadyBuiltElement() {
    val tuple = TupleType.Builder()
        .element(TypeIdentifier("A"))
        .build()
    assertEquals("(A)", tuple.text)
    assertEquals("A", tuple.types[0].text)
  }

  @Test
  fun testShouldBuildTupleWithArguments() {
    val tuple = TupleType.Builder()
        .labelledElement("a", "A")
        .labelledElement("b").optional { it.type("B") }
        .build()
    assertEquals("(a: A, b: B?)", tuple.text)
    assertEquals("A", tuple.types[0].text)
    assertEquals("B?", tuple.types[1].text)
    assertEquals("a", tuple.labels[0])
    assertEquals("b", tuple.labels[1])
  }
}
