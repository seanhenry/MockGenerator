@available(macOS 7, *)
class AvailableClass {
    @available(macOS 9.0, *)
    var property: String? { return nil }
    @available(macOS, deprecated, message: "Don't use this")
    func method() // Like in a swift header
}

class AvailableClassMock: AvailableClass {

    var invokedPropertyGetter = false
    var invokedPropertyGetterCount = 0
    var stubbedProperty: String!
    override var property: String? {
        invokedPropertyGetter = true
        invokedPropertyGetterCount += 1
        return stubbedProperty
    }
    var invokedMethod = false
    var invokedMethodCount = 0

    override func method() {
        invokedMethod = true
        invokedMethodCount += 1
    }
}
