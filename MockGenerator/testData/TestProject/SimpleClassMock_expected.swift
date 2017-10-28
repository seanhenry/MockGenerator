@testable import MockableTypes

class SimpleClassMock: SimpleClass {

    var invokedMethod = false
    override func method() {
        invokedMethod = true
    }

    var invokedAnotherMethod = false
    override func anotherMethod() {
        invokedAnotherMethod = true
    }
}
