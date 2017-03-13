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
    func optionalParams(_ closure: (String?, Int!) -> ())
}

class Mock: ClosureProtocol {
<caret>
}
