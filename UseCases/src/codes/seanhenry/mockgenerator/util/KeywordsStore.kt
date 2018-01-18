package codes.seanhenry.mockgenerator.util

class KeywordsStore {

  private val keywords: Set<String> = setOf(
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
      "for",
      "guard",
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

  fun isSwiftKeyword(input: String): Boolean {
    return keywords.contains(input)
  }
}
