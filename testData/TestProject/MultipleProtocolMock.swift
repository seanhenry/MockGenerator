protocol ProtocolA {
    func a()
}

protocol ProtocolB {
    func b()
}

protocol ProtocolC {
    func c()
}

class Mock: ProtocolA, ProtocolB, ProtocolC {
<caret>
}
