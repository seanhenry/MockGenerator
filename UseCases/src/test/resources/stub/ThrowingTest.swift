func throwingMethod() throws {
}
var stubbedThrowingReturnMethodResult: String! = ""
func throwingReturnMethod() throws -> String {
return stubbedThrowingReturnMethodResult
}
func throwingClosure(closure: () throws -> ()) {
}
func throwingClosureArgument(closure: (String) throws -> (String)) {
}