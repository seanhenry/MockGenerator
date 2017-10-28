package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.TuplePropertyDeclaration
import junit.framework.TestCase
import kotlin.test.assertEquals

class SwiftStringTupleForwardCallTest : TestCase() {

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

  fun testEscapesKeywords() {
    val keywords = listOf(
        "associatedtype",
        "class",
        "deinit",
        "enum",
        "extension",
        "fileprivate",
        "func",
        "import",
        "init",
        "inout",
        "internal",
        "let",
        "open",
        "operator",
        "private",
        "protocol",
        "public",
        "static",
        "struct",
        "subscript",
        "typealias",
        "var",
        "break",
        "case",
        "continue",
        "default",
        "defer",
        "do",
        "else",
        "fallthrough",
        "if",
        "in",
        "repeat",
        "return",
        "switch",
        "where",
        "while",
        "as",
        "Any",
        "catch",
        "false",
        "is",
        "nil",
        "rethrows",
        "super",
        "self",
        "Self",
        "throw",
        "throws",
        "true",
        "try"
    )
    keywords.forEach {
      assertEscapesKeyword(it)
    }
  }

  private fun assertEscapesKeyword(keyword: String) {
    val property = TuplePropertyDeclaration("name", listOf(
        TuplePropertyDeclaration.TupleParameter(keyword, "Type"),
        TuplePropertyDeclaration.TupleParameter("param2", "()")
    ))
    assertEquals("(`$keyword`, ())", SwiftStringTupleForwardCall().transform(property))
  }
}
