protocol InheritedProtocol {
    func inherited(method: String)
}

protocol InheritingProtocol: InheritedProtocol {
    func inheriting()
    func inherited(overloaded: Int)
}

class Mock: InheritingProtocol {
<caret>
}
