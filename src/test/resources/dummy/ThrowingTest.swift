func throwingMethod() throws {
}
func throwingReturnMethod() throws -> String {
return ""
}
func throwingClosure(closure: () throws -> ()) {
}
func throwingClosureArgument(closure: (String) throws -> (String)) {
}