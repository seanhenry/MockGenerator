import Foundation

typealias Completion = (Int) -> (String)

protocol ClosureProtocol {
    typealias T = (String) -> ()
    func map(closure: () -> ())
    func flatMap(closure: () -> Void)
    func filter(closure: (String) -> Bool)
    func typealiasClosure(closure: Completion)
    func internalTypealiasClosure(closure: T)
    func multi(animations: (Int) -> (), completion: (Bool) -> ())
    func optional(animations: ((Int) -> ())?, completion: ((Bool) -> ())?)
    func escaping(closure: @escaping () -> ())
    func returnClosure() -> (() -> ())
    func returnClosureArgs() -> (Int, String) -> (String)
    func optionalParam(_ closure: (String?) -> ())
    func optionalParams(_ closure: (String?, Int!) -> ())
    func parse(response data: Data) // should not resolve Data and treat as closure
}

class Mock: ClosureProtocol {

    var invokedMap = false
    func map(closure: () -> ()) {
        invokedMap = true
        closure()
    }
    var invokedFlatMap = false
    func flatMap(closure: () -> Void) {
        invokedFlatMap = true
        closure()
    }
    var invokedFilter = false
    var stubbedFilterClosureResult: String?
    func filter(closure: (String) -> Bool) {
        invokedFilter = true
        if let result = stubbedFilterClosureResult {
            closure(result)
        }
    }
    var invokedTypealiasClosure = false
    var stubbedTypealiasClosureClosureResult: Int?
    func typealiasClosure(closure: Completion) {
        invokedTypealiasClosure = true
        if let result = stubbedTypealiasClosureClosureResult {
            closure(result)
        }
    }
    var invokedInternalTypealiasClosure = false
    var stubbedInternalTypealiasClosureClosureResult: String?
    func internalTypealiasClosure(closure: ClosureProtocol.T) {
        invokedInternalTypealiasClosure = true
        if let result = stubbedInternalTypealiasClosureClosureResult {
            closure(result)
        }
    }
    var invokedMulti = false
    var stubbedMultiAnimationsResult: Int?
    var stubbedMultiCompletionResult: Bool?
    func multi(animations: (Int) -> (), completion: (Bool) -> ()) {
        invokedMulti = true
        if let result = stubbedMultiAnimationsResult {
            animations(result)
        }
        if let result = stubbedMultiCompletionResult {
            completion(result)
        }
    }
    var invokedOptional = false
    var stubbedOptionalAnimationsResult: Int?
    var stubbedOptionalCompletionResult: Bool?
    func optional(animations: ((Int) -> ())?, completion: ((Bool) -> ())?) {
        invokedOptional = true
        if let result = stubbedOptionalAnimationsResult {
            animations?(result)
        }
        if let result = stubbedOptionalCompletionResult {
            completion?(result)
        }
    }
    var invokedEscaping = false
    func escaping(closure: @escaping () -> ()) {
        invokedEscaping = true
        closure()
    }
    var invokedReturnClosure = false
    var stubbedReturnClosureResult: (() -> ())!
    func returnClosure() -> (() -> ()) {
        invokedReturnClosure = true
        return stubbedReturnClosureResult
    }
    var invokedReturnClosureArgs = false
    var stubbedReturnClosureArgsResult: ((Int, String) -> (String))!
    func returnClosureArgs() -> (Int, String) -> (String) {
        invokedReturnClosureArgs = true
        return stubbedReturnClosureArgsResult
    }
    var invokedOptionalParam = false
    var stubbedOptionalParamClosureResult: String??
    func optionalParam(_ closure: (String?) -> ()) {
        invokedOptionalParam = true
        if let result = stubbedOptionalParamClosureResult {
            closure(result)
        }
    }
    var invokedOptionalParams = false
    var stubbedOptionalParamsClosureResult: (String?, Int!)?
    func optionalParams(_ closure: (String?, Int!) -> ()) {
        invokedOptionalParams = true
        if let result = stubbedOptionalParamsClosureResult {
            closure(result)
        }
    }
    var invokedParse = false
    var invokedParseParameters: (data: Data, Void)?
    func parse(response data: Data) {
        invokedParse = true
        invokedParseParameters = (data, ())
    }
}
