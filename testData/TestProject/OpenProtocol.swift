protocol OpenProtocol {
    var variable: String { get set }
    func method(param: String, closure: () -> ()) -> String
}
