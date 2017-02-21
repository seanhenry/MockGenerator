protocol SimpleProtocol {
    func simpleMethod()
}

class Mock: SimpleProtocol {
    
    var invokedSimpleMethod = false
    func simpleMethod() {
        invokedSimpleMethod = true
    }
}
