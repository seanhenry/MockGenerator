protocol OverloadProtocol {
    func method() -> String
    func method(withString: String)
    func method(withInt: Int)
    func method(with float: Float)
    func method(with string: String, at int: Int)
    func method(with string: String, at int: Int, for float: Float)
}

class Mock: OverloadProtocol {

    var invokedMethod = false
    var stubbedMethodResult: String!
    func method() -> String {
        invokedMethod = true
        return stubbedMethodResult
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
    var invokedMethodWithAt = false
    var invokedMethodWithAtParameters: (string: String, int: Int)?
    func method(with string: String, at int: Int) {
        invokedMethodWithAt = true
        invokedMethodWithAtParameters = (string, int)
    }
    var invokedMethodWithAtFor = false
    var invokedMethodWithAtForParameters: (string: String, int: Int, float: Float)?
    func method(with string: String, at int: Int, for float: Float) {
        invokedMethodWithAtFor = true
        invokedMethodWithAtForParameters = (string, int, float)
    }
}
