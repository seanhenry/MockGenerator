@testable import MockableTypes

class ClassAndProtocolMock: ClassAndProtocol, ClassAndProtocolMockProtocol {

    var invokedClassAndProtocolProtocolPropertyGetter = false
    var invokedClassAndProtocolProtocolPropertyGetterCount = 0
    var stubbedClassAndProtocolProtocolProperty: String!
    override var classAndProtocolProtocolProperty: String? {
        invokedClassAndProtocolProtocolPropertyGetter = true
        invokedClassAndProtocolProtocolPropertyGetterCount += 1
        return stubbedClassAndProtocolProtocolProperty
    }
    var invokedClassAndProtocolMockProtocolPropertyGetter = false
    var invokedClassAndProtocolMockProtocolPropertyGetterCount = 0
    var stubbedClassAndProtocolMockProtocolProperty: String!
    override var classAndProtocolMockProtocolProperty: String? {
        invokedClassAndProtocolMockProtocolPropertyGetter = true
        invokedClassAndProtocolMockProtocolPropertyGetterCount += 1
        return stubbedClassAndProtocolMockProtocolProperty
    }
    var invokedClassAndProtocolProtocolMethod = false
    var invokedClassAndProtocolProtocolMethodCount = 0

    override func classAndProtocolProtocolMethod() {
        invokedClassAndProtocolProtocolMethod = true
        invokedClassAndProtocolProtocolMethodCount += 1
    }

    var invokedClassAndProtocolMockProtocolMethod = false
    var invokedClassAndProtocolMockProtocolMethodCount = 0

    override func classAndProtocolMockProtocolMethod() {
        invokedClassAndProtocolMockProtocolMethod = true
        invokedClassAndProtocolMockProtocolMethodCount += 1
    }
}
