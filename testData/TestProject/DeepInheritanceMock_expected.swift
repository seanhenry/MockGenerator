class MockDeepInheritance: TopMostProtocol, TopMostSiblingProtocol {

    var invokedTopMost = false
    var invokedTopMostCount = 0
    func topMost() {
        invokedTopMost = true
    }
    var invokedTopSibling = false
    var invokedTopSiblingCount = 0
    func topSibling() {
        invokedTopSibling = true
    }
    var invokedMiddle = false
    var invokedMiddleCount = 0
    func middle() {
        invokedMiddle = true
    }
    var invokedMiddleSibling1 = false
    var invokedMiddleSibling1Count = 0
    func middleSibling1() {
        invokedMiddleSibling1 = true
    }
    var invokedMiddleSibling2 = false
    var invokedMiddleSibling2Count = 0
    func middleSibling2() {
        invokedMiddleSibling2 = true
    }
    var invokedDeepest = false
    var invokedDeepestCount = 0
    func deepest() {
        invokedDeepest = true
    }
    var invokedDeepestSibling = false
    var invokedDeepestSiblingCount = 0
    func deepestSibling() {
        invokedDeepestSibling = true
    }
    var invokedDeepestCousin = false
    var invokedDeepestCousinCount = 0
    func deepestCousin() {
        invokedDeepestCousin = true
    }
}
