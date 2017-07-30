@testable import MockGeneratorTest

class MockMultiAssociatedTypeProtocol<SomeType, AnotherType>: MultiAssociatedTypeProtocol {

    var invokedDoSomething = false
    var invokedDoSomethingCount = 0
    var invokedDoSomethingParameters: (with: SomeType, Void)?
    var invokedDoSomethingParametersList = [(with: SomeType, Void)]()
    var stubbedDoSomethingResult: AnotherType!

    func doSomething(with: SomeType) -> AnotherType {
        invokedDoSomething = true
        invokedDoSomethingCount += 1
        invokedDoSomethingParameters = (with, ())
        invokedDoSomethingParametersList.append((with, ()))
        return stubbedDoSomethingResult
    }

    var invokedDoSomethingElse = false
    var invokedDoSomethingElseCount = 0
    var invokedDoSomethingElseParameters: (with: SomeType?, Void)?
    var invokedDoSomethingElseParametersList = [(with: SomeType?, Void)]()
    var stubbedDoSomethingElseResult: SomeType!

    func doSomethingElse(with: SomeType!) -> SomeType? {
        invokedDoSomethingElse = true
        invokedDoSomethingElseCount += 1
        invokedDoSomethingElseParameters = (with, ())
        invokedDoSomethingElseParametersList.append((with, ()))
        return stubbedDoSomethingElseResult
    }
}
