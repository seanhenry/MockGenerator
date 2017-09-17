package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.entities.TuplePropertyDeclaration
import junit.framework.TestCase

class SwiftStringTupleAssignmentTest: TestCase() {

  fun testTransformsToPropertyStatement() {
    val property = TuplePropertyDeclaration("name", listOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Type1"),
        TuplePropertyDeclaration.TupleParameter("param2", "Type2")
    ))
    assertEquals("name = (param1, param2)", SwiftStringTupleAssignment().transform(property))
  }

  fun testTransformsVoidIntoShorthand() {
    val property = TuplePropertyDeclaration("name", listOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Type1"),
        TuplePropertyDeclaration.TupleParameter("param2", "Void")
    ))
    assertEquals("name = (param1, ())", SwiftStringTupleAssignment().transform(property))
  }

  fun testTransformsVoidShorthandIntoShorthandParameter() {
    val property = TuplePropertyDeclaration("name", listOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Type1"),
        TuplePropertyDeclaration.TupleParameter("param2", "()")
    ))
    assertEquals("name = (param1, ())", SwiftStringTupleAssignment().transform(property))
  }

  fun testTransformsEmptyTuple() {
    val property = TuplePropertyDeclaration("name", emptyList())
    assertEquals("name = ()", SwiftStringTupleAssignment().transform(property))
  }
}
