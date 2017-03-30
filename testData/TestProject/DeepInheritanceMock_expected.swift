class MockDeepInheritance: TopMostProtocol, TopMostSiblingProtocol {

    var invokedTopMost = false
    func topMost() {
        invokedTopMost = true
    }
    var invokedTopSibling = false
    func topSibling() {
        invokedTopSibling = true
    }
    var invokedMiddle = false
    func middle() {
        invokedMiddle = true
    }
    var invokedMiddleSibling1 = false
    func middleSibling1() {
        invokedMiddleSibling1 = true
    }
    var invokedMiddleSibling2 = false
    func middleSibling2() {
        invokedMiddleSibling2 = true
    }
    var invokedDeepest = false
    func deepest() {
        invokedDeepest = true
    }
    var invokedDeepestSibling = false
    func deepestSibling() {
        invokedDeepestSibling = true
    }
    var invokedDeepestCousin = false
    func deepestCousin() {
        invokedDeepestCousin = true
    }
}
