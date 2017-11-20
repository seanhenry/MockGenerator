@testable import MockableTypes

class InferredTypeClassMock: InferredTypeClass {

    var invokedStringSetter = false
    var invokedStringSetterCount = 0
    var invokedString: String?
    var invokedStringList = [String]()
    var invokedStringGetter = false
    var invokedStringGetterCount = 0
    var stubbedString: String! = ""
    override var string: String {
        set {
            invokedStringSetter = true
            invokedStringSetterCount += 1
            invokedString = newValue
            invokedStringList.append(newValue)
        }
        get {
            invokedStringGetter = true
            invokedStringGetterCount += 1
            return stubbedString
        }
    }
    var invokedStringLiteralSetter = false
    var invokedStringLiteralSetterCount = 0
    var invokedStringLiteral: String?
    var invokedStringLiteralList = [String]()
    var invokedStringLiteralGetter = false
    var invokedStringLiteralGetterCount = 0
    var stubbedStringLiteral: String! = ""
    override var stringLiteral: String {
        set {
            invokedStringLiteralSetter = true
            invokedStringLiteralSetterCount += 1
            invokedStringLiteral = newValue
            invokedStringLiteralList.append(newValue)
        }
        get {
            invokedStringLiteralGetter = true
            invokedStringLiteralGetterCount += 1
            return stubbedStringLiteral
        }
    }
    var invokedDictionaryLiteralSetter = false
    var invokedDictionaryLiteralSetterCount = 0
    var invokedDictionaryLiteral: [String: Int]?
    var invokedDictionaryLiteralList = [[String: Int]]()
    var invokedDictionaryLiteralGetter = false
    var invokedDictionaryLiteralGetterCount = 0
    var stubbedDictionaryLiteral: [String: Int]! = [:]
    override var dictionaryLiteral: [String: Int] {
        set {
            invokedDictionaryLiteralSetter = true
            invokedDictionaryLiteralSetterCount += 1
            invokedDictionaryLiteral = newValue
            invokedDictionaryLiteralList.append(newValue)
        }
        get {
            invokedDictionaryLiteralGetter = true
            invokedDictionaryLiteralGetterCount += 1
            return stubbedDictionaryLiteral
        }
    }
    var invokedMethodAssignedSetter = false
    var invokedMethodAssignedSetterCount = 0
    var invokedMethodAssigned: String?
    var invokedMethodAssignedList = [String]()
    var invokedMethodAssignedGetter = false
    var invokedMethodAssignedGetterCount = 0
    var stubbedMethodAssigned: String! = ""
    override var methodAssigned: String {
        set {
            invokedMethodAssignedSetter = true
            invokedMethodAssignedSetterCount += 1
            invokedMethodAssigned = newValue
            invokedMethodAssignedList.append(newValue)
        }
        get {
            invokedMethodAssignedGetter = true
            invokedMethodAssignedGetterCount += 1
            return stubbedMethodAssigned
        }
    }
    var invokedPropertyAssignedSetter = false
    var invokedPropertyAssignedSetterCount = 0
    var invokedPropertyAssigned: String?
    var invokedPropertyAssignedList = [String]()
    var invokedPropertyAssignedGetter = false
    var invokedPropertyAssignedGetterCount = 0
    var stubbedPropertyAssigned: String! = ""
    override var propertyAssigned: String {
        set {
            invokedPropertyAssignedSetter = true
            invokedPropertyAssignedSetterCount += 1
            invokedPropertyAssigned = newValue
            invokedPropertyAssignedList.append(newValue)
        }
        get {
            invokedPropertyAssignedGetter = true
            invokedPropertyAssignedGetterCount += 1
            return stubbedPropertyAssigned
        }
    }
}
