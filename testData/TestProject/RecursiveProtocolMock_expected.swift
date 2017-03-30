class MockRecursiveProtocol: InheritingProtocol {

    var invokedInheriting = false
    func inheriting() {
        invokedInheriting = true
    }
    var invokedInheritedOverloaded = false
    var invokedInheritedOverloadedParameters: (overloaded: Int, Void)?
    func inherited(overloaded: Int) {
        invokedInheritedOverloaded = true
        invokedInheritedOverloadedParameters = (overloaded, ())
    }
    var invokedInheritedMethod = false
    var invokedInheritedMethodParameters: (method: String, Void)?
    func inherited(method: String) {
        invokedInheritedMethod = true
        invokedInheritedMethodParameters = (method, ())
    }
}
