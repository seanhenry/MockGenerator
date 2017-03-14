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

    var invokedA = false
    func a() {
        invokedA = true
    }
    var invokedB = false
    func b() {
        invokedB = true
    }
    var invokedC = false
    func c() {
        invokedC = true
    }
}
