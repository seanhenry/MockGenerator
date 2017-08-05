@testable import MockableTypes

public class MockPublicProtocol: PublicProtocol {

    public var invokedVariableSetter = false
    public var invokedVariableSetterCount = 0
    public var invokedVariable: Object?
    public var invokedVariableList = [Object]()
    public var invokedVariableGetter = false
    public var invokedVariableGetterCount = 0
    public var stubbedVariable: Object!
    public var variable: Object {
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
    public var stubbedMethodResult: Object!

    public func method(param: String, closure: () -> ()) -> Object {
        invokedMethod = true
        invokedMethodCount += 1
        invokedMethodParameters = (param, ())
        invokedMethodParametersList.append((param, ()))
        closure()
        return stubbedMethodResult
    }
}
