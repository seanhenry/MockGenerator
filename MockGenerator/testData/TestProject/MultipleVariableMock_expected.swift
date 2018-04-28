@testable import MockableTypes

class MultipleVariableMock: MultipleVariable {

    var invokedASetter = false
    var invokedASetterCount = 0
    var invokedA: String?
    var invokedAList = [String]()
    var invokedAGetter = false
    var invokedAGetterCount = 0
    var stubbedA: String! = ""
    override var a: String {
        set {
            invokedASetter = true
            invokedASetterCount += 1
            invokedA = newValue
            invokedAList.append(newValue)
        }
        get {
            invokedAGetter = true
            invokedAGetterCount += 1
            return stubbedA
        }
    }
    var invokedBSetter = false
    var invokedBSetterCount = 0
    var invokedB: Int?
    var invokedBList = [Int]()
    var invokedBGetter = false
    var invokedBGetterCount = 0
    var stubbedB: Int! = 0
    override var b: Int {
        set {
            invokedBSetter = true
            invokedBSetterCount += 1
            invokedB = newValue
            invokedBList.append(newValue)
        }
        get {
            invokedBGetter = true
            invokedBGetterCount += 1
            return stubbedB
        }
    }
}
