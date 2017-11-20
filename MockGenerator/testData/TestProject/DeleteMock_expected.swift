@testable import MockableTypes

class DeleteMock<T>: SimpleClass where T: Any {

    var invokedMethod = false
    var invokedMethodCount = 0

    override func method() {
        invokedMethod = true
        invokedMethodCount += 1
    }

    var invokedAnotherMethod = false
    var invokedAnotherMethodCount = 0

    override func anotherMethod() {
        invokedAnotherMethod = true
        invokedAnotherMethodCount += 1
    }
};class ShouldNotDeleteThis {}
