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
var stubbedReturnGenericResult: String!
func returnGeneric() -> Optional<String> {
invokedReturnGeneric = true
invokedReturnGenericCount += 1
return stubbedReturnGenericResult
}
var invokedReturnOptionalGeneric = false
var invokedReturnOptionalGenericCount = 0
var stubbedReturnOptionalGenericResult: String!
func returnOptionalGeneric() -> Optional<String>? {
invokedReturnOptionalGeneric = true
invokedReturnOptionalGenericCount += 1
return stubbedReturnOptionalGenericResult
}
var invokedReturnClosure = false
var invokedReturnClosureCount = 0
var stubbedReturnClosureResult: (() -> ())! = { }
func returnClosure() -> () -> () {
invokedReturnClosure = true
invokedReturnClosureCount += 1
return stubbedReturnClosureResult
}
var invokedReturnComplicatedClosure = false
var invokedReturnComplicatedClosureCount = 0
var stubbedReturnComplicatedClosureResult: ((String, Int?) -> (UInt))! = { _, _ in return 0 }
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
var stubbedReturnClosureResult: (() -> ())! = { }
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
var invokedClosureA = false
var invokedClosureACount = 0
var stubbedClosureAResult: (() -> ())! = { }
func closureA() -> () -> () {
invokedClosureA = true
invokedClosureACount += 1
return stubbedClosureAResult
}
var invokedClosureB = false
var invokedClosureBCount = 0
var stubbedClosureBResult: (() -> (Void))! = { }
func closureB() -> () -> (Void) {
invokedClosureB = true
invokedClosureBCount += 1
return stubbedClosureBResult
}
var invokedClosureC = false
var invokedClosureCCount = 0
var stubbedClosureCResult: (() -> Void)! = { }
func closureC() -> () -> Void {
invokedClosureC = true
invokedClosureCCount += 1
return stubbedClosureCResult
}
var invokedClosureD = false
var invokedClosureDCount = 0
var stubbedClosureDResult: ((String, Int) -> ())! = { _, _ in }
func closureD() -> (String, Int) -> () {
invokedClosureD = true
invokedClosureDCount += 1
return stubbedClosureDResult
}
var invokedClosureE = false
var invokedClosureECount = 0
var stubbedClosureEResult: (() -> (String))! = { return "" }
func closureE() -> () -> (String) {
invokedClosureE = true
invokedClosureECount += 1
return stubbedClosureEResult
}