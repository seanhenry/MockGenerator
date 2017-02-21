protocol OverloadProtocol {
    func method() -> String
    func method(withString: String) -> String
}

class Mock: OverloadProtocol {

    var invokedMethod = false
    func method() -> String {
        invokedMethod = true
    }
    var invokedMethodWithString = false
    var invokedMethodWithStringParameters: (withString: String, Void)?
    func method(withString: String) -> String {
        invokedMethodWithString = true
        invokedMethodWithStringParameters = (withString, ())
    }
}
