@testable import MockableTypes

class ThrowingClassMock: ThrowingClass {

    var invokedThrowingMethod = false
    var invokedThrowingMethodCount = 0
    var stubbedThrowingMethodError: Error?

    override func throwingMethod() throws {
        invokedThrowingMethod = true
        invokedThrowingMethodCount += 1
        if let error = stubbedThrowingMethodError {
            throw error
        }
    }
}
