var stubbedReturnTypeResult: String! = ""
func returnType() -> String {
return stubbedReturnTypeResult
}
var stubbedReturnTupleResult: (String, Int?)! = ("", nil)
func returnTuple() -> (String, Int?) {
return stubbedReturnTupleResult
}
var stubbedReturnLabelledTupleResult: (s: String, i: Int?)! = ("", nil)
func returnLabelledTuple() -> (s: String, i: Int?) {
return stubbedReturnLabelledTupleResult
}
var stubbedReturnOptionalResult: Int!
func returnOptional() -> Int? {
return stubbedReturnOptionalResult
}
var stubbedReturnIUOResult: UInt!
func returnIUO() -> UInt! {
return stubbedReturnIUOResult
}
var stubbedReturnGenericResult: String!
func returnGeneric() -> Optional<String> {
return stubbedReturnGenericResult
}
var stubbedReturnOptionalGenericResult: String!
func returnOptionalGeneric() -> Optional<String>? {
return stubbedReturnOptionalGenericResult
}
var stubbedReturnClosureResult: (() -> ())! = { }
func returnClosure() -> () -> () {
return stubbedReturnClosureResult
}
var stubbedReturnComplicatedClosureResult: ((String, Int?) -> (UInt))! = { _, _ in return 0 }
func returnComplicatedClosure() -> ((String, Int?) -> (UInt)) {
return stubbedReturnComplicatedClosureResult
}
var stubbedReturnOptionalClosureResult: (() -> ())!
func returnOptionalClosure() -> (() -> ())? {
return stubbedReturnOptionalClosureResult
}
var stubbedReturnExplicitVoidResult: Void! = ()
func returnExplicitVoid() -> Void {
return stubbedReturnExplicitVoidResult
}
var stubbedReturnClosureResult: (() -> ())! = { }
func returnClosure() -> (() -> ()) {
return stubbedReturnClosureResult
}
var stubbedReturnClosureArgsResult: ((Int, String) -> (String))! = { _, _ in return "" }
func returnClosureArgs() -> (Int, String) -> (String) {
return stubbedReturnClosureArgsResult
}
var stubbedClosureAResult: (() -> ())! = { }
func closureA() -> () -> () {
return stubbedClosureAResult
}
var stubbedClosureBResult: (() -> (Void))! = { }
func closureB() -> () -> (Void) {
return stubbedClosureBResult
}
var stubbedClosureCResult: (() -> Void)! = { }
func closureC() -> () -> Void {
return stubbedClosureCResult
}
var stubbedClosureDResult: ((String, Int) -> ())! = { _, _ in }
func closureD() -> (String, Int) -> () {
return stubbedClosureDResult
}
var stubbedClosureEResult: (() -> (String))! = { return "" }
func closureE() -> () -> (String) {
return stubbedClosureEResult
}