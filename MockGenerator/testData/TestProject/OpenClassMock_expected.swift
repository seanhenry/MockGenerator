@testable import MockableTypes

open class OpenClassMock: OpenClass {

    public convenience init() {
        self.init(a: nil)
    }

    open var invokedInternalPropertyGetter = false
    open var invokedInternalPropertyGetterCount = 0
    open var stubbedInternalProperty: String!
    open override var internalProperty: String? {
        invokedInternalPropertyGetter = true
        invokedInternalPropertyGetterCount += 1
        return stubbedInternalProperty
    }
    open var invokedPublicPropertyGetter = false
    open var invokedPublicPropertyGetterCount = 0
    open var stubbedPublicProperty: String!
    open override var publicProperty: String? {
        invokedPublicPropertyGetter = true
        invokedPublicPropertyGetterCount += 1
        return stubbedPublicProperty
    }
    open var invokedOpenPropertyGetter = false
    open var invokedOpenPropertyGetterCount = 0
    open var stubbedOpenProperty: String!
    open override var openProperty: String? {
        invokedOpenPropertyGetter = true
        invokedOpenPropertyGetterCount += 1
        return stubbedOpenProperty
    }
    open var invokedInternalMethod = false
    open var invokedInternalMethodCount = 0

    open override func internalMethod() {
        invokedInternalMethod = true
        invokedInternalMethodCount += 1
    }

    open var invokedPublicMethod = false
    open var invokedPublicMethodCount = 0

    open override func publicMethod() {
        invokedPublicMethod = true
        invokedPublicMethodCount += 1
    }

    open var invokedOpenMethod = false
    open var invokedOpenMethodCount = 0

    open override func openMethod() {
        invokedOpenMethod = true
        invokedOpenMethodCount += 1
    }
}
