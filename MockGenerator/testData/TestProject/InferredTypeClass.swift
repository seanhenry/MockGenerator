typealias StringAlias = String

class InferredTypeClass {
    var string = String("")
    var stringLiteral = ""
    var dictionaryLiteral = [String: Int]()
    var methodAssigned = InferredTypeClass.method()
    var propertyAssigned = InferredTypeClass.property

    private static func method() -> String {
        return "Method!"
    }

    private static let property = "Property!"
}
