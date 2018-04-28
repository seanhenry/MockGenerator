package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class TupleTypeBuilderTest : TestCase() {

  fun testShouldBuildEmptyTuple() {
    val tuple = TupleType.Builder().build()
    assertEquals("()", tuple.text)
    assertTrue(tuple.elements.isEmpty())
  }

  fun testShouldBuildSimpleTypeTuple() {
    val tuple = TupleType.Builder()
        .element("Int")
        .build()
    assertEquals("(Int)", tuple.text)
    assertEquals("Int", tuple.elements[0].text)
  }

  fun testShouldBuildComplexTypeTuple() {
    val tuple = TupleType.Builder()
        .element().optional { it.type("A") }
        .element().array { it.type("B") }
        .build()
    assertEquals("(A?, [B])", tuple.text)
    assertEquals("A?", tuple.elements[0].text)
    assertEquals("[B]", tuple.elements[1].text)
  }

  fun testShouldBuildAlreadyBuiltElement() {
    val tuple = TupleType.Builder()
        .element(TypeIdentifier("A"))
        .build()
    assertEquals("(A)", tuple.text)
    assertEquals("A", tuple.elements[0].text)
  }
}
