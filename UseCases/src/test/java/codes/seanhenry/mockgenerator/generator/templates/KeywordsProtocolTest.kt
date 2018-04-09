package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.ast.Method
import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.generator.MockTransformer

class KeywordsProtocolTest: MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.add(
        Property("`class`", "String", true, "var `class`: String { get set }")
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

  override fun getExpected(): String {
    return """
    var invokedClassSetter = false
    var invokedClassSetterCount = 0
    var invokedClass: String?
    var invokedClassList = [String]()
    var invokedClassGetter = false
    var invokedClassGetterCount = 0
    var stubbedClass: String! = ""
    var `class`: String {
    set {
    invokedClassSetter = true
    invokedClassSetterCount += 1
    invokedClass = newValue
    invokedClassList.append(newValue)
    }
    get {
    invokedClassGetter = true
    invokedClassGetterCount += 1
    return stubbedClass
    }
    }
    var invokedRun = false
    var invokedRunCount = 0
    var invokedRunParameters: (for: Int, Void)?
    var invokedRunParametersList = [(for: Int, Void)]()
    func run(for: Int) {
    invokedRun = true
    invokedRunCount += 1
    invokedRunParameters = (`for`, ())
    invokedRunParametersList.append((`for`, ()))
    }
    var invokedFor = false
    var invokedForCount = 0
    var invokedForParameters: (in: String, Void)?
    var invokedForParametersList = [(in: String, Void)]()
    func `for`(in: String) {
    invokedFor = true
    invokedForCount += 1
    invokedForParameters = (`in`, ())
    invokedForParametersList.append((`in`, ()))
    }
    var invokedStatements = false
    var invokedStatementsCount = 0
    var invokedStatementsParameters: (break: Int, case: Int, continue: Int, default: Int, defer: Int, do: Int, else: Int, fallthrough: Int, for: Int, guard: Int, if: Int, in: Int, repeat: Int, return: Int, switch: Int, where: Int, while: Int)?
    var invokedStatementsParametersList = [(break: Int, case: Int, continue: Int, default: Int, defer: Int, do: Int, else: Int, fallthrough: Int, for: Int, guard: Int, if: Int, in: Int, repeat: Int, return: Int, switch: Int, where: Int, while: Int)]()
    func statements(break: Int, case: Int, continue: Int, default: Int, defer: Int, do: Int, else: Int, fallthrough: Int, for: Int, guard: Int, if: Int, in: Int, repeat: Int, return: Int, switch: Int, where: Int, while: Int) {
    invokedStatements = true
    invokedStatementsCount += 1
    invokedStatementsParameters = (`break`, `case`, `continue`, `default`, `defer`, `do`, `else`, `fallthrough`, `for`, `guard`, `if`, `in`, `repeat`, `return`, `switch`, `where`, `while`)
    invokedStatementsParametersList.append((`break`, `case`, `continue`, `default`, `defer`, `do`, `else`, `fallthrough`, `for`, `guard`, `if`, `in`, `repeat`, `return`, `switch`, `where`, `while`))
    }
    var invokedDeclarations = false
    var invokedDeclarationsCount = 0
    var invokedDeclarationsParameters: (associatedtype: Int, class: Int, deinit: Int, enum: Int, extension: Int, fileprivate: Int, func: Int, import: Int, init: Int, `inout`: Int, internal: Int, `let`: Int, open: Int, operator: Int, private: Int, protocol: Int, public: Int, static: Int, struct: Int, subscript: Int, typealias: Int, `var`: Int)?
    var invokedDeclarationsParametersList = [(associatedtype: Int, class: Int, deinit: Int, enum: Int, extension: Int, fileprivate: Int, func: Int, import: Int, init: Int, `inout`: Int, internal: Int, `let`: Int, open: Int, operator: Int, private: Int, protocol: Int, public: Int, static: Int, struct: Int, subscript: Int, typealias: Int, `var`: Int)]()
    func declarations(associatedtype: Int, class: Int, deinit: Int, enum: Int, extension: Int, fileprivate: Int, func: Int, import: Int, init: Int, `inout`: Int, internal: Int, `let`: Int, open: Int, operator: Int, private: Int, protocol: Int, public: Int, static: Int, struct: Int, subscript: Int, typealias: Int, `var`: Int) {
    invokedDeclarations = true
    invokedDeclarationsCount += 1
    invokedDeclarationsParameters = (`associatedtype`, `class`, `deinit`, `enum`, `extension`, `fileprivate`, `func`, `import`, `init`, `inout`, `internal`, `let`, `open`, `operator`, `private`, `protocol`, `public`, `static`, `struct`, `subscript`, `typealias`, `var`)
    invokedDeclarationsParametersList.append((`associatedtype`, `class`, `deinit`, `enum`, `extension`, `fileprivate`, `func`, `import`, `init`, `inout`, `internal`, `let`, `open`, `operator`, `private`, `protocol`, `public`, `static`, `struct`, `subscript`, `typealias`, `var`))
    }
    var invokedExpressionsAndTypes = false
    var invokedExpressionsAndTypesCount = 0
    var invokedExpressionsAndTypesParameters: (as: Int, Any: Int, catch: Int, false: Int, is: Int, nil: Int, rethrows: Int, super: Int, Self: Int, throw: Int, throws: Int, true: Int, try: Int)?
    var invokedExpressionsAndTypesParametersList = [(as: Int, Any: Int, catch: Int, false: Int, is: Int, nil: Int, rethrows: Int, super: Int, Self: Int, throw: Int, throws: Int, true: Int, try: Int)]()
    func expressionsAndTypes(as: Int, Any: Int, catch: Int, false: Int, is: Int, nil: Int, rethrows: Int, super: Int, Self: Int, throw: Int, throws: Int, true: Int, try: Int) {
    invokedExpressionsAndTypes = true
    invokedExpressionsAndTypesCount += 1
    invokedExpressionsAndTypesParameters = (`as`, `Any`, `catch`, `false`, `is`, `nil`, `rethrows`, `super`, `Self`, `throw`, `throws`, `true`, `try`)
    invokedExpressionsAndTypesParametersList.append((`as`, `Any`, `catch`, `false`, `is`, `nil`, `rethrows`, `super`, `Self`, `throw`, `throws`, `true`, `try`))
    }
    var invokedReserved = false
    var invokedReservedCount = 0
    var invokedReservedParameters: (associativity: Int, convenience: Int, dynamic: Int, didSet: Int, final: Int, get: Int, infix: Int, indirect: Int, lazy: Int, left: Int, mutating: Int, none: Int, nonmutating: Int, optional: Int, override: Int, postfix: Int, precedence: Int, prefix: Int, Protocol: Int, required: Int, right: Int, set: Int, Type: Int, unowned: Int, weak: Int, willSet: Int)?
    var invokedReservedParametersList = [(associativity: Int, convenience: Int, dynamic: Int, didSet: Int, final: Int, get: Int, infix: Int, indirect: Int, lazy: Int, left: Int, mutating: Int, none: Int, nonmutating: Int, optional: Int, override: Int, postfix: Int, precedence: Int, prefix: Int, Protocol: Int, required: Int, right: Int, set: Int, Type: Int, unowned: Int, weak: Int, willSet: Int)]()
    func reserved(associativity: Int, convenience: Int, dynamic: Int, didSet: Int, final: Int, get: Int, infix: Int, indirect: Int, lazy: Int, left: Int, mutating: Int, none: Int, nonmutating: Int, optional: Int, override: Int, postfix: Int, precedence: Int, prefix: Int, Protocol: Int, required: Int, right: Int, set: Int, Type: Int, unowned: Int, weak: Int, willSet: Int) {
    invokedReserved = true
    invokedReservedCount += 1
    invokedReservedParameters = (associativity, convenience, dynamic, didSet, final, get, infix, indirect, lazy, left, mutating, none, nonmutating, optional, override, postfix, precedence, prefix, Protocol, required, right, set, Type, unowned, weak, willSet)
    invokedReservedParametersList.append((associativity, convenience, dynamic, didSet, final, get, infix, indirect, lazy, left, mutating, none, nonmutating, optional, override, postfix, precedence, prefix, Protocol, required, right, set, Type, unowned, weak, willSet))
    }
      """.trimIndent()
  }
}
