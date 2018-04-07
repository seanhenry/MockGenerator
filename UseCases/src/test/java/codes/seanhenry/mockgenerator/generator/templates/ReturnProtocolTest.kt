package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.ast.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ReturnProtocolTest: MockGeneratorTestTemplate {
  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("returnType")
            .returnType("String")
            .build(),
        Method.Builder("returnTuple")
            .returnType("(String, Int?)")
            .build(),
        Method.Builder("returnLabelledTuple")
            .returnType("(s: String, i: Int?)")
            .build(),
        Method.Builder("returnOptional")
            .returnType().optional { it.type("Int") }
            .build(),
        Method.Builder("returnIUO")
            .returnType().optional { it.unwrapped().type("UInt") }
            .build(),
        Method("returnGeneric", "Optional<String>", "", "func returnGeneric() -> Optional<String>"),
        Method("returnOptionalGeneric", "Optional<String>?", "", "func returnOptionalGeneric() -> Optional<String>?"),
        Method.Builder("returnClosure")
            .returnType().function { }
            .build(),
        Method.Builder("returnComplicatedClosure")
            .returnType().bracket().function { func ->
              func.argument("String")
                  .argument().optional { it.type("Int") }
                  .returnType("(UInt)")
            }
            .build(),
        Method.Builder("returnOptionalClosure")
            .returnType().optional { it.type().function { } }
            .build(),
        Method.Builder("returnExplicitVoid")
            .returnType("Void")
            .build(),
        Method.Builder("returnClosure")
            .returnType().bracket().function { }
            .build(),
        Method.Builder("returnClosureArgs")
            .returnType().function { func ->
              func.argument("Int")
                  .argument("String")
                  .returnType("(String)")
            }
            .build()
    )
  }

  override fun getExpected(): String {
    return """
    var invokedReturnType = false
    var invokedReturnTypeCount = 0
    var stubbedReturnTypeResult: String! = ""
    func returnType() -> String {
    invokedReturnType = true
    invokedReturnTypeCount += 1
    return stubbedReturnTypeResult
    }
    var invokedReturnTuple = false
    var invokedReturnTupleCount = 0
    var stubbedReturnTupleResult: (String, Int?)!
    func returnTuple() -> (String, Int?) {
    invokedReturnTuple = true
    invokedReturnTupleCount += 1
    return stubbedReturnTupleResult
    }
    var invokedReturnLabelledTuple = false
    var invokedReturnLabelledTupleCount = 0
    var stubbedReturnLabelledTupleResult: (s: String, i: Int?)!
    func returnLabelledTuple() -> (s: String, i: Int?) {
    invokedReturnLabelledTuple = true
    invokedReturnLabelledTupleCount += 1
    return stubbedReturnLabelledTupleResult
    }
    var invokedReturnOptional = false
    var invokedReturnOptionalCount = 0
    var stubbedReturnOptionalResult: Int!
    func returnOptional() -> Int? {
    invokedReturnOptional = true
    invokedReturnOptionalCount += 1
    return stubbedReturnOptionalResult
    }
    var invokedReturnIUO = false
    var invokedReturnIUOCount = 0
    var stubbedReturnIUOResult: UInt!
    func returnIUO() -> UInt! {
    invokedReturnIUO = true
    invokedReturnIUOCount += 1
    return stubbedReturnIUOResult
    }
    var invokedReturnGeneric = false
    var invokedReturnGenericCount = 0
    var stubbedReturnGenericResult: Optional<String>!
    func returnGeneric() -> Optional<String> {
    invokedReturnGeneric = true
    invokedReturnGenericCount += 1
    return stubbedReturnGenericResult
    }
    var invokedReturnOptionalGeneric = false
    var invokedReturnOptionalGenericCount = 0
    var stubbedReturnOptionalGenericResult: Optional<String>!
    func returnOptionalGeneric() -> Optional<String>? {
    invokedReturnOptionalGeneric = true
    invokedReturnOptionalGenericCount += 1
    return stubbedReturnOptionalGenericResult
    }
    var invokedReturnClosure = false
    var invokedReturnClosureCount = 0
    var stubbedReturnClosureResult: (() -> ())!
    func returnClosure() -> () -> () {
    invokedReturnClosure = true
    invokedReturnClosureCount += 1
    return stubbedReturnClosureResult
    }
    var invokedReturnComplicatedClosure = false
    var invokedReturnComplicatedClosureCount = 0
    var stubbedReturnComplicatedClosureResult: ((String, Int?) -> (UInt))!
    func returnComplicatedClosure() -> ((String, Int?) -> (UInt)) {
    invokedReturnComplicatedClosure = true
    invokedReturnComplicatedClosureCount += 1
    return stubbedReturnComplicatedClosureResult
    }
    var invokedReturnOptionalClosure = false
    var invokedReturnOptionalClosureCount = 0
    var stubbedReturnOptionalClosureResult: (() -> ())!
    func returnOptionalClosure() -> (() -> ())? {
    invokedReturnOptionalClosure = true
    invokedReturnOptionalClosureCount += 1
    return stubbedReturnOptionalClosureResult
    }
    var invokedReturnExplicitVoid = false
    var invokedReturnExplicitVoidCount = 0
    var stubbedReturnExplicitVoidResult: Void!
    func returnExplicitVoid() -> Void {
    invokedReturnExplicitVoid = true
    invokedReturnExplicitVoidCount += 1
    return stubbedReturnExplicitVoidResult
    }
    var invokedReturnClosure = false
    var invokedReturnClosureCount = 0
    var stubbedReturnClosureResult: (() -> ())!
    func returnClosure() -> (() -> ()) {
    invokedReturnClosure = true
    invokedReturnClosureCount += 1
    return stubbedReturnClosureResult
    }
    var invokedReturnClosureArgs = false
    var invokedReturnClosureArgsCount = 0
    var stubbedReturnClosureArgsResult: ((Int, String) -> (String))!
    func returnClosureArgs() -> (Int, String) -> (String) {
    invokedReturnClosureArgs = true
    invokedReturnClosureArgsCount += 1
    return stubbedReturnClosureArgsResult
    }
      """.trimIndent()
  }
}
