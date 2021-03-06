@testable import MockableTypes

class MockDiamondInheritanceProtocol: DiamondC, DiamondB {

    var invokedC = false
    var invokedCCount = 0

    func c() {
        invokedC = true
        invokedCCount += 1
    }

    var invokedB = false
    var invokedBCount = 0

    func b() {
        invokedB = true
        invokedBCount += 1
    }

    var invokedA = false
    var invokedACount = 0

    func a() {
        invokedA = true
        invokedACount += 1
    }
}
