var invokedMethod = false
var invokedMethodCount = 0
var forwardToOriginalMethod = false
override func method() {
invokedMethod = true
invokedMethodCount += 1
if forwardToOriginalMethod {
super.method()
return
}
}
var invokedMethodA = false
var invokedMethodACount = 0
var invokedMethodAParameters: (a: Int, b: Int, d: Int)?
var invokedMethodAParametersList = [(a: Int, b: Int, d: Int)]()
var forwardToOriginalMethodA = false
override func method(a: Int, _ b: Int, c d: Int) {
invokedMethodA = true
invokedMethodACount += 1
invokedMethodAParameters = (a, b, d)
invokedMethodAParametersList.append((a, b, d))
if forwardToOriginalMethodA {
super.method(a: a, b, c: d)
return
}
}
var invokedReturnMethod = false
var invokedReturnMethodCount = 0
var stubbedReturnMethodResult: Int! = 0
var forwardToOriginalReturnMethod = false
override func returnMethod() -> Int {
invokedReturnMethod = true
invokedReturnMethodCount += 1
if forwardToOriginalReturnMethod {
return super.returnMethod()
}
return stubbedReturnMethodResult
}
var invokedForwardNoStubs = false
var invokedForwardNoStubsCount = 0
var shouldInvokeForwardNoStubsA = false
var stubbedForwardNoStubsError: Error?
var forwardToOriginalForwardNoStubs = false
override func forwardNoStubs(a: () -> ()) throws {
invokedForwardNoStubs = true
invokedForwardNoStubsCount += 1
if forwardToOriginalForwardNoStubs {
try super.forwardNoStubs(a: a)
return
}
if shouldInvokeForwardNoStubsA {
a()
}
if let error = stubbedForwardNoStubsError {
throw error
}
}
var invokedThrowing = false
var invokedThrowingCount = 0
var stubbedThrowingError: Error?
var stubbedThrowingResult: Int! = 0
var forwardToOriginalThrowing = false
override func throwing() throws -> Int {
invokedThrowing = true
invokedThrowingCount += 1
if forwardToOriginalThrowing {
return try super.throwing()
}
if let error = stubbedThrowingError {
throw error
}
return stubbedThrowingResult
}
var invokedRethrowing = false
var invokedRethrowingCount = 0
var stubbedRethrowingResult: Int! = 0
var forwardToOriginalRethrowing = false
override func rethrowing() rethrows -> Int {
invokedRethrowing = true
invokedRethrowingCount += 1
if forwardToOriginalRethrowing {
return try super.rethrowing()
}
return stubbedRethrowingResult
}
var invokedProtocolMethod = false
var invokedProtocolMethodCount = 0
func protocolMethod() {
invokedProtocolMethod = true
invokedProtocolMethodCount += 1
}