class MockRecursiveProtocol: InheritingProtocol {

    var invokedInheriting = false
    var invokedInheritingCount = 0
    func inheriting() {
        invokedInheriting = true
    }
    var invokedInheritedOverloaded = false
    var invokedInheritedOverloadedCount = 0
    var invokedInheritedOverloadedParameters: (overloaded: Int, Void)?
    func inherited(overloaded: Int) {
        invokedInheritedOverloaded = true
        invokedInheritedOverloadedParameters = (overloaded, ())
    }
    var invokedInheritedMethod = false
    var invokedInheritedMethodCount = 0
    var invokedInheritedMethodParameters: (method: String, Void)?
    func inherited(method: String) {
        invokedInheritedMethod = true
        invokedInheritedMethodParameters = (method, ())
    }
}
