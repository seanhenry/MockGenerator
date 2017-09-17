package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.TuplePropertyDeclaration
import junit.framework.TestCase

class SwiftStringTupleForwardCallTest: TestCase() {

  fun testTransformsToTuple() {
    val property = TuplePropertyDeclaration("name", listOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Type1"),
        TuplePropertyDeclaration.TupleParameter("param2", "Type2")
    ))
    assertEquals("(param1, param2)", SwiftStringTupleForwardCall().transform(property))
  }

  fun testTransformsVoidIntoShorthand() {
    val property = TuplePropertyDeclaration("name", listOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Type1"),
        TuplePropertyDeclaration.TupleParameter("param2", "Void")
    ))
    assertEquals("(param1, ())", SwiftStringTupleForwardCall().transform(property))
  }

  fun testTransformsVoidShorthandIntoShorthandParameter() {
    val property = TuplePropertyDeclaration("name", listOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Type1"),
        TuplePropertyDeclaration.TupleParameter("param2", "()")
    ))
    assertEquals("(param1, ())", SwiftStringTupleForwardCall().transform(property))
  }

  fun testTransformsEmptyTuple() {
    val property = TuplePropertyDeclaration("name", emptyList())
    assertEquals("()", SwiftStringTupleForwardCall().transform(property))
  }
}
