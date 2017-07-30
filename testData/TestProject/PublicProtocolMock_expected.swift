@testable import MockGeneratorTest

public class MockPublicProtocol: PublicProtocol {

    public var invokedVariable: String?
    public var stubbedVariable: String!
    public var variable: String {
        set {
            invokedVariable = newValue
        }
        get {
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
