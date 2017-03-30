class MockDiamondInheritanceProtocol: DiamondC, DiamondB {

    var invokedC = false
    func c() {
        invokedC = true
    }
    var invokedB = false
    func b() {
        invokedB = true
    }
    var invokedA = false
    func a() {
        invokedA = true
    }
}
