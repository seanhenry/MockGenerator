protocol OverloadProtocol {
    func method() -> String
    func method(withString: String)
    func method(withInt: Int)
    func method(with float: Float)
    func method(with string: String, at int: Int)
    func method(with string: String, at int: Int, for float: Float)
}

class Mock: OverloadProtocol {
<caret>
}
