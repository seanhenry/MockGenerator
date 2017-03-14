protocol InheritedProtocol {
    func inherited()
}

protocol InheritingProtocol: InheritedProtocol {
    func inheriting()
}

class Mock: InheritingProtocol {
<caret>
}
