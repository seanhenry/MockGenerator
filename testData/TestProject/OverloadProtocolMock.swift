protocol OverloadProtocol {
    func method()
    func method(withString: String)
    func method(withInt: Int)
    func method(with float: Float)
}

class Mock: OverloadProtocol {
<caret>
}
