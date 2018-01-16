@testable import MockableTypes

class ThrowingProtocolMock: ThrowingProtocol {

    var invokedThrowingMethod = false
    var invokedThrowingMethodCount = 0
    var stubbedThrowingMethodError: Error?

    func throwingMethod() throws {
        invokedThrowingMethod = true
        invokedThrowingMethodCount += 1
        if let error = stubbedThrowingMethodError {
            throw error
        }
    }
}
