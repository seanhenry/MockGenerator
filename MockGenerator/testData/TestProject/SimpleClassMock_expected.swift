@testable import MockableTypes

class SimpleClassMock: SimpleClass {

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
    var invokedMethod = false
    var invokedMethodCount = 0

    override func method() {
        invokedMethod = true
        invokedMethodCount += 1
    }

    var invokedAnotherMethod = false
    var invokedAnotherMethodCount = 0

    override func anotherMethod() {
        invokedAnotherMethod = true
        invokedAnotherMethodCount += 1
    }
}
