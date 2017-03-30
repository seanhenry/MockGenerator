class MockMultiAssociatedTypeProtocol<SomeType, AnotherType>: MultiAssociatedTypeProtocol {

    var invokedDoSomething = false
    var invokedDoSomethingParameters: (with: SomeType, Void)?
    var stubbedDoSomethingResult: AnotherType!
    func doSomething(with: SomeType) -> AnotherType {
        invokedDoSomething = true
        invokedDoSomethingParameters = (with, ())
        return stubbedDoSomethingResult
    }
    var invokedDoSomethingElse = false
    var invokedDoSomethingElseParameters: (with: SomeType?, Void)?
    var stubbedDoSomethingElseResult: SomeType!
    func doSomethingElse(with: SomeType!) -> SomeType? {
        invokedDoSomethingElse = true
        invokedDoSomethingElseParameters = (with, ())
        return stubbedDoSomethingElseResult
    }
}
