@testable import MockableTypes

internal class InternalClassMock: OpenClass {

    convenience init() {
        self.init(a: nil)
    }

    var invokedInternalPropertyGetter = false
    var invokedInternalPropertyGetterCount = 0
    var stubbedInternalProperty: String!
    override var internalProperty: String? {
        invokedInternalPropertyGetter = true
        invokedInternalPropertyGetterCount += 1
        return stubbedInternalProperty
    }
    var invokedPublicPropertyGetter = false
    var invokedPublicPropertyGetterCount = 0
    var stubbedPublicProperty: String!
    override var publicProperty: String? {
        invokedPublicPropertyGetter = true
        invokedPublicPropertyGetterCount += 1
        return stubbedPublicProperty
    }
    var invokedOpenPropertyGetter = false
    var invokedOpenPropertyGetterCount = 0
    var stubbedOpenProperty: String!
    override var openProperty: String? {
        invokedOpenPropertyGetter = true
        invokedOpenPropertyGetterCount += 1
        return stubbedOpenProperty
    }
    var invokedInternalMethod = false
    var invokedInternalMethodCount = 0

    override func internalMethod() {
        invokedInternalMethod = true
        invokedInternalMethodCount += 1
    }

    var invokedPublicMethod = false
    var invokedPublicMethodCount = 0

    override func publicMethod() {
        invokedPublicMethod = true
        invokedPublicMethodCount += 1
    }

    var invokedOpenMethod = false
    var invokedOpenMethodCount = 0

    override func openMethod() {
        invokedOpenMethod = true
        invokedOpenMethodCount += 1
    }
}
