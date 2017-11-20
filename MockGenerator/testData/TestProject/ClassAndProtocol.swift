protocol ClassAndProtocolMockProtocol {
    var classAndProtocolMockProtocolProperty: String? { get }
    func classAndProtocolMockProtocolMethod()
}

protocol ClassAndProtocolProtocol {
    var classAndProtocolProtocolProperty: String? { get }
    func classAndProtocolProtocolMethod()
}

class ClassAndProtocol: ClassAndProtocolProtocol {
    var classAndProtocolProtocolProperty: String? { return nil }
    func classAndProtocolProtocolMethod() { }
    var classAndProtocolMockProtocolProperty: String? { return nil }
    func classAndProtocolMockProtocolMethod() { }
}
