var invokedTypealiasClosure = false
var invokedTypealiasClosureCount = 0
var stubbedTypealiasClosureClosureResult: (Int, Void)?
func typealiasClosure(closure: Completion) {
invokedTypealiasClosure = true
invokedTypealiasClosureCount += 1
if let result = stubbedTypealiasClosureClosureResult {
_ = closure(result.0)
}
}
var invokedInternalTypealiasClosure = false
var invokedInternalTypealiasClosureCount = 0
var stubbedInternalTypealiasClosureClosureResult: (String, Void)?
func internalTypealiasClosure(closure: T) {
invokedInternalTypealiasClosure = true
invokedInternalTypealiasClosureCount += 1
if let result = stubbedInternalTypealiasClosureClosureResult {
closure(result.0)
}
}