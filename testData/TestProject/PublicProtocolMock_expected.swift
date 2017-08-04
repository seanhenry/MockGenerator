@testable import MockGeneratorTest

public class MockPublicProtocol: PublicProtocol {

    public var invokedVariableSetter = false
    public var invokedVariableSetterCount = 0
    public var invokedVariable: String?
    public var invokedVariableList = [String]()
    public var invokedVariableGetter = false
    public var invokedVariableGetterCount = 0
    public var stubbedVariable: String!
    public var variable: String {
        set {
            invokedVariableSetter = true
            invokedVariableSetterCount += 1
            invokedVariable = newValue
            invokedVariableList.append(newValue)
        }
        get {
            invokedVariableGetter = true
            invokedVariableGetterCount += 1
            return stubbedVariable
        }
    }
    public var invokedMethod = false
    public var invokedMethodCount = 0
    public var invokedMethodParameters: (param: String, Void)?
    public var invokedMethodParametersList = [(param: String, Void)]()
    public var stubbedMethodResult: String!

    public func method(param: String, closure: () -> ()) -> String {
        invokedMethod = true
        invokedMethodCount += 1
        invokedMethodParameters = (param, ())
        invokedMethodParametersList.append((param, ()))
        closure()
        return stubbedMethodResult
    }
}
