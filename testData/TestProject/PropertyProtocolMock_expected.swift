@testable import MockGeneratorTest

class MockPropertyProtocol: PropertyProtocol {

    var invokedReadWrite: String?
    var stubbedReadWrite: String!
    var readWrite: String {
        set {
            invokedReadWrite = newValue
        }
        get {
            return stubbedReadWrite
        }
    }
    var stubbedReadOnly: Int!
    var readOnly: Int {
        return stubbedReadOnly
    }
    var invokedOptional: UInt?
    var stubbedOptional: UInt!
    var optional: UInt? {
        set {
            invokedOptional = newValue
        }
        get {
            return stubbedOptional
        }
    }
    var invokedUnwrapped: String?
    var stubbedUnwrapped: String!
    var unwrapped: String! {
        set {
            invokedUnwrapped = newValue
        }
        get {
            return stubbedUnwrapped
        }
    }
    var invokedWeakVar: AnyObject?
    var stubbedWeakVar: AnyObject!
    weak var weakVar: AnyObject? {
        set {
            invokedWeakVar = newValue
        }
        get {
            return stubbedWeakVar
        }
    }
    var invokedMethod = false
    var invokedMethodCount = 0

    func method() {
        invokedMethod = true
        invokedMethodCount += 1
    }
}
