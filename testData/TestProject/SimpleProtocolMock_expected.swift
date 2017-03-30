class AnotherDeclarationInTheFileShouldNotBeAffected {

    func shouldNotInterfere() {

    }
}

class SimpleProtocolMock: SimpleProtocol {

    var invokedSimpleMethod = false
    func simpleMethod() {
        invokedSimpleMethod = true
    }
    var invokedAnotherMethod = false
    var invokedAnotherMethodParameters: (var1: String, var2: Int, var3: Double)?
    func anotherMethod(var1: String, var2: Int, var3: Double) {
        invokedAnotherMethod = true
        invokedAnotherMethodParameters = (var1, var2, var3)
    }
    var invokedReturnMethod = false
    var invokedReturnMethodParameters: (hello: String, Void)?
    var stubbedReturnMethodResult: String!
    func returnMethod(_ hello: String) -> String {
        invokedReturnMethod = true
        invokedReturnMethodParameters = (hello, ())
        return stubbedReturnMethodResult
    }
    var invokedDuplicateParam = false
    var invokedDuplicateParamParameters: (var1: String, Void)?
    func duplicateParam(var1: String) {
        invokedDuplicateParam = true
        invokedDuplicateParamParameters = (var1, ())
    }
}
