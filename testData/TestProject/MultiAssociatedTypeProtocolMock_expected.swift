class MockMultiAssociatedTypeProtocol<SomeType, AnotherType>: MultiAssociatedTypeProtocol {

    var invokedDoSomething = false
    var invokedDoSomethingCount = 0
    var invokedDoSomethingParameters: (with: SomeType, Void)?
    var stubbedDoSomethingResult: AnotherType!
    func doSomething(with: SomeType) -> AnotherType {
        invokedDoSomething = true
        invokedDoSomethingCount += 1
        invokedDoSomethingParameters = (with, ())
        return stubbedDoSomethingResult
    }
    var invokedDoSomethingElse = false
    var invokedDoSomethingElseCount = 0
    var invokedDoSomethingElseParameters: (with: SomeType?, Void)?
    var stubbedDoSomethingElseResult: SomeType!
    func doSomethingElse(with: SomeType!) -> SomeType? {
        invokedDoSomethingElse = true
        invokedDoSomethingElseCount += 1
        invokedDoSomethingElseParameters = (with, ())
        return stubbedDoSomethingElseResult
    }
}
