protocol InheritedProtocol {
    func inherited()
}

protocol InheritingProtocol: InheritedProtocol {
    func inheriting()
}

class Mock: InheritingProtocol {

    var invokedInheriting = false
    func inheriting() {
        invokedInheriting = true
    }
    var invokedInherited = false
    func inherited() {
        invokedInherited = true
    }
}
