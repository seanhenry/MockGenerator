class MockAssociatedTypeProtocol<SomeType>: AssociatedTypeProtocol {

    var invokedDoSomething = false
    var invokedDoSomethingCount = 0
    var invokedDoSomethingParameters: (with: SomeType, Void)?
    var stubbedDoSomethingResult: SomeType!
    func doSomething(with: SomeType) -> SomeType {
        invokedDoSomething = true
        invokedDoSomethingParameters = (with, ())
        return stubbedDoSomethingResult
    }
}
