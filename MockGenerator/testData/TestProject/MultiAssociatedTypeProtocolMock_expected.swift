@testable import MockableTypes

class MockMultiAssociatedTypeProtocol<AnotherType, SomeType>: MultiAssociatedTypeProtocol {

    var invokedDoSomethingElse = false
    var invokedDoSomethingElseCount = 0
    var invokedDoSomethingElseParameters: (with: SomeType?, Void)?
    var invokedDoSomethingElseParametersList = [(with: SomeType?, Void)]()
    var stubbedDoSomethingElseResult: AnotherType!

    func doSomethingElse(with: SomeType!) -> AnotherType? {
        invokedDoSomethingElse = true
        invokedDoSomethingElseCount += 1
        invokedDoSomethingElseParameters = (with, ())
        invokedDoSomethingElseParametersList.append((with, ()))
        return stubbedDoSomethingElseResult
    }

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
