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
}
