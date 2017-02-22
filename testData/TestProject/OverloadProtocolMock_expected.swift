protocol OverloadProtocol {
    func method()
    func method(withString: String)
    func method(withInt: Int)
    func method(with float: Float)
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
    var invokedMethodWithInt = false
    var invokedMethodWithIntParameters: (withInt: Int, Void)?
    func method(withInt: Int) {
        invokedMethodWithInt = true
        invokedMethodWithIntParameters = (withInt, ())
    }
    var invokedMethodWith = false
    var invokedMethodWithParameters: (float: Float, Void)?
    func method(with float: Float) {
        invokedMethodWith = true
        invokedMethodWithParameters = (float, ())
    }
}
