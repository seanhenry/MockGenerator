class MockClosureProtocol: ClosureProtocol {

    var invokedMap = false
    var invokedMapCount = 0
    func map(closure: () -> ()) {
        invokedMap = true
        closure()
    }
    var invokedFlatMap = false
    var invokedFlatMapCount = 0
    func flatMap(closure: () -> Void) {
        invokedFlatMap = true
        closure()
    }
    var invokedFilter = false
    var invokedFilterCount = 0
    var stubbedFilterClosureResult: (String, Void)?
    func filter(closure: (String) -> Bool) {
        invokedFilter = true
        if let result = stubbedFilterClosureResult {
            closure(result.0)
        }
    }
    var invokedTypealiasClosure = false
    var invokedTypealiasClosureCount = 0
    var stubbedTypealiasClosureClosureResult: (Int, Void)?
    func typealiasClosure(closure: Completion) {
        invokedTypealiasClosure = true
        if let result = stubbedTypealiasClosureClosureResult {
            closure(result.0)
        }
    }
    var invokedInternalTypealiasClosure = false
    var invokedInternalTypealiasClosureCount = 0
    var stubbedInternalTypealiasClosureClosureResult: (String, Void)?
    func internalTypealiasClosure(closure: ClosureProtocol.T) {
        invokedInternalTypealiasClosure = true
        if let result = stubbedInternalTypealiasClosureClosureResult {
            closure(result.0)
        }
    }
    var invokedMulti = false
    var invokedMultiCount = 0
    var stubbedMultiAnimationsResult: (Int, Void)?
    var stubbedMultiCompletionResult: (Bool, Void)?
    func multi(animations: (Int) -> (), completion: (Bool) -> ()) {
        invokedMulti = true
        if let result = stubbedMultiAnimationsResult {
            animations(result.0)
        }
        if let result = stubbedMultiCompletionResult {
            completion(result.0)
        }
    }
    var invokedOptional = false
    var invokedOptionalCount = 0
    var stubbedOptionalAnimationsResult: (Int, Void)?
    var stubbedOptionalCompletionResult: (Bool, Void)?
    func optional(animations: ((Int) -> ())?, completion: ((Bool) -> ())?) {
        invokedOptional = true
        if let result = stubbedOptionalAnimationsResult {
            animations?(result.0)
        }
        if let result = stubbedOptionalCompletionResult {
            completion?(result.0)
        }
    }
    var invokedEscaping = false
    var invokedEscapingCount = 0
    func escaping(closure: @escaping () -> ()) {
        invokedEscaping = true
        closure()
    }
    var invokedReturnClosure = false
    var invokedReturnClosureCount = 0
    var stubbedReturnClosureResult: (() -> ())!
    func returnClosure() -> (() -> ()) {
        invokedReturnClosure = true
        return stubbedReturnClosureResult
    }
    var invokedReturnClosureArgs = false
    var invokedReturnClosureArgsCount = 0
    var stubbedReturnClosureArgsResult: ((Int, String) -> (String))!
    func returnClosureArgs() -> (Int, String) -> (String) {
        invokedReturnClosureArgs = true
        return stubbedReturnClosureArgsResult
    }
    var invokedOptionalParam = false
    var invokedOptionalParamCount = 0
    var stubbedOptionalParamClosureResult: (String?, Void)?
    func optionalParam(_ closure: (String?) -> ()) {
        invokedOptionalParam = true
        if let result = stubbedOptionalParamClosureResult {
            closure(result.0)
        }
    }
    var invokedOptionalParams = false
    var invokedOptionalParamsCount = 0
    var stubbedOptionalParamsClosureResult: (String?, Int!)?
    func optionalParams(_ closure: (String?, Int!) -> ()) {
        invokedOptionalParams = true
        if let result = stubbedOptionalParamsClosureResult {
            closure(result.0, result.1)
        }
    }
    var invokedParse = false
    var invokedParseCount = 0
    var invokedParseParameters: (data: Data, Void)?
    func parse(response data: Data) {
        invokedParse = true
        invokedParseParameters = (data, ())
    }
}
