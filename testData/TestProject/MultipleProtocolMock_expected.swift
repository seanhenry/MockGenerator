class MockMultipleProtocol: ProtocolA, ProtocolB, ProtocolC {

    var invokedA = false
    var invokedACount = 0
    func a() {
        invokedA = true
    }
    var invokedB = false
    var invokedBCount = 0
    func b() {
        invokedB = true
    }
    var invokedC = false
    var invokedCCount = 0
    func c() {
        invokedC = true
    }
}
