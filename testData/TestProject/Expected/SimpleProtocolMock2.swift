protocol SimpleProtocol2 {
    func simpleMethod()
    func anotherMethod()
}

class Mock: SimpleProtocol2 {

    var invokedSimpleMethod = false
    func simpleMethod() {
        invokedSimpleMethod = true
    }
    var invokedAnotherMethod = false
    func anotherMethod() {
        invokedAnotherMethod = true
    }
}
