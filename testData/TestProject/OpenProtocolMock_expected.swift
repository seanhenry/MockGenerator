@testable import MockGeneratorTest

open class MockOpenProtocol: OpenProtocol {

    open var invokedVariableSetter = false
    open var invokedVariableSetterCount = 0
    open var invokedVariable: String?
    open var invokedVariableList = [String]()
    open var invokedVariableGetter = false
    open var invokedVariableGetterCount = 0
    open var stubbedVariable: String!
    open var variable: String {
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
    open var invokedMethod = false
    open var invokedMethodCount = 0
    open var invokedMethodParameters: (param: String, Void)?
    open var invokedMethodParametersList = [(param: String, Void)]()
    open var stubbedMethodResult: String!

    open func method(param: String, closure: () -> ()) -> String {
        invokedMethod = true
        invokedMethodCount += 1
        invokedMethodParameters = (param, ())
        invokedMethodParametersList.append((param, ()))
        closure()
        return stubbedMethodResult
    }
}
