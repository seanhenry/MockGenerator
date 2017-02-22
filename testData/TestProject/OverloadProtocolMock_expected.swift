protocol OverloadProtocol {
    func method()
    func method(withString: String)
}

class Mock: OverloadProtocol {

    var invokedMethod = false
    func method() {
        invokedMethod = true
    }
    var invokedMethodWithString = false
    var invokedMethodWithStringParameters: (withString: String, Void)?
    func method(withString: String) {
        invokedMethodWithString = true
        invokedMethodWithStringParameters = (withString, ())
    }
}
