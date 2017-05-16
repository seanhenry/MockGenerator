class MockAssociatedTypeProtocol<SomeType>: AssociatedTypeProtocol {

    var invokedDoSomething = false
    var invokedDoSomethingCount = 0
    var invokedDoSomethingParameters: (with: SomeType, Void)?
    var invokedDoSomethingParametersList = [(with: SomeType, Void)]()
    var stubbedDoSomethingResult: SomeType!
    func doSomething(with: SomeType) -> SomeType {
        invokedDoSomething = true
        invokedDoSomethingCount += 1
        invokedDoSomethingParameters = (with, ())
        invokedDoSomethingParametersList.append((with, ()))
        return stubbedDoSomethingResult
    }
}
