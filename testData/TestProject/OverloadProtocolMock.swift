protocol OverloadProtocol {
    func method()
    func method(withString: String)
}

class Mock: OverloadProtocol {
<caret>
}
