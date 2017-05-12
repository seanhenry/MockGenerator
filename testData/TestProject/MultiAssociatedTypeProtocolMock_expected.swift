class MockMultiAssociatedTypeProtocol<SomeType, AnotherType>: MultiAssociatedTypeProtocol {

    var invokedDoSomething = false
    var invokedDoSomethingCount = 0
    var invokedDoSomethingParameters: (with: SomeType, Void)?
    var stubbedDoSomethingResult: AnotherType!
    func doSomething(with: SomeType) -> AnotherType {
        invokedDoSomething = true
        invokedDoSomethingParameters = (with, ())
        return stubbedDoSomethingResult
    }
    var invokedDoSomethingElse = false
    var invokedDoSomethingElseCount = 0
    var invokedDoSomethingElseParameters: (with: SomeType?, Void)?
    var stubbedDoSomethingElseResult: SomeType!
    func doSomethingElse(with: SomeType!) -> SomeType? {
        invokedDoSomethingElse = true
        invokedDoSomethingElseParameters = (with, ())
        return stubbedDoSomethingElseResult
    }
}
