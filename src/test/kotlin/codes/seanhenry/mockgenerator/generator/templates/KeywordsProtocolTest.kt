package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.generator.MockTransformer

class KeywordsProtocolTest: MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.add(
        Property.Builder("`class`")
            .type("String")
            .build()
    )
    generator.add(
        Method.Builder("run")
            .parameter("for") { it.type("Int") }
            .build(),
        Method.Builder("`for`")
            .parameter("in") { it.type("String") }
            .build(),
        Method.Builder("statements")
            .parameter("break") { it.type("Int") }
            .parameter("case") { it.type("Int") }
            .parameter("continue") { it.type("Int") }
            .parameter("default") { it.type("Int") }
            .parameter("defer") { it.type("Int") }
            .parameter("do") { it.type("Int") }
            .parameter("else") { it.type("Int") }
            .parameter("fallthrough") { it.type("Int") }
            .parameter("for") { it.type("Int") }
            .parameter("guard") { it.type("Int") }
            .parameter("if") { it.type("Int") }
            .parameter("in") { it.type("Int") }
            .parameter("repeat") { it.type("Int") }
            .parameter("return") { it.type("Int") }
            .parameter("switch") { it.type("Int") }
            .parameter("where") { it.type("Int") }
            .parameter("while") { it.type("Int") }
            .build(),
        Method.Builder("declarations")
            .parameter("associatedtype") { it.type("Int") }
            .parameter("class") { it.type("Int") }
            .parameter("deinit") { it.type("Int") }
            .parameter("enum") { it.type("Int") }
            .parameter("extension") { it.type("Int") }
            .parameter("fileprivate") { it.type("Int") }
            .parameter("func") { it.type("Int") }
            .parameter("import") { it.type("Int") }
            .parameter("init") { it.type("Int") }
            .parameter("`inout`") { it.type("Int") }
            .parameter("internal") { it.type("Int") }
            .parameter("`let`") { it.type("Int") }
            .parameter("open") { it.type("Int") }
            .parameter("operator") { it.type("Int") }
            .parameter("private") { it.type("Int") }
            .parameter("protocol") { it.type("Int") }
            .parameter("public") { it.type("Int") }
            .parameter("static") { it.type("Int") }
            .parameter("struct") { it.type("Int") }
            .parameter("subscript") { it.type("Int") }
            .parameter("typealias") { it.type("Int") }
            .parameter("`var`") { it.type("Int") }
            .build(),
        Method.Builder("expressionsAndTypes")
            .parameter("as") { it.type("Int") }
            .parameter("Any") { it.type("Int") }
            .parameter("catch") { it.type("Int") }
            .parameter("false") { it.type("Int") }
            .parameter("is") { it.type("Int") }
            .parameter("nil") { it.type("Int") }
            .parameter("rethrows") { it.type("Int") }
            .parameter("super") { it.type("Int") }
            .parameter("Self") { it.type("Int") }
            .parameter("throw") { it.type("Int") }
            .parameter("throws") { it.type("Int") }
            .parameter("true") { it.type("Int") }
            .parameter("try") { it.type("Int") }
            .build(),
        Method.Builder("reserved")
            .parameter("associativity") { it.type("Int") }
            .parameter("convenience") { it.type("Int") }
            .parameter("dynamic") { it.type("Int") }
            .parameter("didSet") { it.type("Int") }
            .parameter("final") { it.type("Int") }
            .parameter("get") { it.type("Int") }
            .parameter("infix") { it.type("Int") }
            .parameter("indirect") { it.type("Int") }
            .parameter("lazy") { it.type("Int") }
            .parameter("left") { it.type("Int") }
            .parameter("mutating") { it.type("Int") }
            .parameter("none") { it.type("Int") }
            .parameter("nonmutating") { it.type("Int") }
            .parameter("optional") { it.type("Int") }
            .parameter("override") { it.type("Int") }
            .parameter("postfix") { it.type("Int") }
            .parameter("precedence") { it.type("Int") }
            .parameter("prefix") { it.type("Int") }
            .parameter("Protocol") { it.type("Int") }
            .parameter("required") { it.type("Int") }
            .parameter("right") { it.type("Int") }
            .parameter("set") { it.type("Int") }
            .parameter("Type") { it.type("Int") }
            .parameter("unowned") { it.type("Int") }
            .parameter("weak") { it.type("Int") }
            .parameter("willSet") { it.type("Int") }
            .build()
    )
  }
}
