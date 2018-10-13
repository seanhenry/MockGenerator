var invokedA = false
var invokedACount = 0
var invokedAParameters: (a: Void, Void)?
var invokedAParametersList = [(a: Void, Void)]()
func a(a: ()) {
invokedA = true
invokedACount += 1
invokedAParameters = ((), ())
invokedAParametersList.append(((), ()))
}