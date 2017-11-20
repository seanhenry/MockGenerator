@testable import MockableTypes

public class PublicClassMock: OpenClass {

    public convenience init() {
        self.init(a: nil)
    }

    public var invokedInternalPropertyGetter = false
    public var invokedInternalPropertyGetterCount = 0
    public var stubbedInternalProperty: String!
    public override var internalProperty: String? {
        invokedInternalPropertyGetter = true
        invokedInternalPropertyGetterCount += 1
        return stubbedInternalProperty
    }
    public var invokedPublicPropertyGetter = false
    public var invokedPublicPropertyGetterCount = 0
    public var stubbedPublicProperty: String!
    public override var publicProperty: String? {
        invokedPublicPropertyGetter = true
        invokedPublicPropertyGetterCount += 1
        return stubbedPublicProperty
    }
    public var invokedOpenPropertyGetter = false
    public var invokedOpenPropertyGetterCount = 0
    public var stubbedOpenProperty: String!
    public override var openProperty: String? {
        invokedOpenPropertyGetter = true
        invokedOpenPropertyGetterCount += 1
        return stubbedOpenProperty
    }
    public var invokedInternalMethod = false
    public var invokedInternalMethodCount = 0

    public override func internalMethod() {
        invokedInternalMethod = true
        invokedInternalMethodCount += 1
    }

    public var invokedPublicMethod = false
    public var invokedPublicMethodCount = 0

    public override func publicMethod() {
        invokedPublicMethod = true
        invokedPublicMethodCount += 1
    }

    public var invokedOpenMethod = false
    public var invokedOpenMethodCount = 0

    public override func openMethod() {
        invokedOpenMethod = true
        invokedOpenMethodCount += 1
    }
}
