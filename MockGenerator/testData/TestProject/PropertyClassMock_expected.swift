@testable import MockableTypes

class PropertyClassMock: PropertyClass {

    var invokedPropertySetter = false
    var invokedPropertySetterCount = 0
    var invokedProperty: String?
    var invokedPropertyList = [String?]()
    var invokedPropertyGetter = false
    var invokedPropertyGetterCount = 0
    var stubbedProperty: String!
    override var property: String? {
        set {
            invokedPropertySetter = true
            invokedPropertySetterCount += 1
            invokedProperty = newValue
            invokedPropertyList.append(newValue)
        }
        get {
            invokedPropertyGetter = true
            invokedPropertyGetterCount += 1
            return stubbedProperty
        }
    }
    var invokedComputedGetter = false
    var invokedComputedGetterCount = 0
    var stubbedComputed: Int! = 0
    override var computed: Int {
        invokedComputedGetter = true
        invokedComputedGetterCount += 1
        return stubbedComputed
    }
}
