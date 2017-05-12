class MockDiamondInheritanceProtocol: DiamondC, DiamondB {

    var invokedC = false
    var invokedCCount = 0
    func c() {
        invokedC = true
    }
    var invokedB = false
    var invokedBCount = 0
    func b() {
        invokedB = true
    }
    var invokedA = false
    var invokedACount = 0
    func a() {
        invokedA = true
    }
}
