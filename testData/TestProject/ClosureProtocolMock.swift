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
<caret>
}
