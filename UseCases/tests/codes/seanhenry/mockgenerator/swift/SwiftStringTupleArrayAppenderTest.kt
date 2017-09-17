package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.TuplePropertyDeclaration
import junit.framework.TestCase

class SwiftStringTupleArrayAppenderTest : TestCase() {

  fun testShouldWrapTupleInCallToAppend() {
    val property = TuplePropertyDeclaration("tuple", arrayListOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Type1"),
        TuplePropertyDeclaration.TupleParameter("param2", "Type2")
    ))
    val result = SwiftStringTupleArrayAppender().transform(property)
    assertEquals("tuple.append((param1, param2))", result)
  }

  fun testShouldWrapAnotherTupleInCallToAppend() {
    val property = TuplePropertyDeclaration("anotherTuple", arrayListOf(
        TuplePropertyDeclaration.TupleParameter("paramA", "Int"),
        TuplePropertyDeclaration.TupleParameter("paramB", "String"),
        TuplePropertyDeclaration.TupleParameter("paramC", "UInt")
    ))
    val result = SwiftStringTupleArrayAppender().transform(property)
    assertEquals("anotherTuple.append((paramA, paramB, paramC))", result)
  }

  fun testShouldWrapTupleVoidValues() {
    val property = TuplePropertyDeclaration("anotherTuple", arrayListOf(
        TuplePropertyDeclaration.TupleParameter("paramA", "Void"),
        TuplePropertyDeclaration.TupleParameter("paramB", "()"),
        TuplePropertyDeclaration.TupleParameter("paramC", "Int")
    ))
    val result = SwiftStringTupleArrayAppender().transform(property)
    assertEquals("anotherTuple.append(((), (), paramC))", result)
  }
}
