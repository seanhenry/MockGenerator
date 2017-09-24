protocol OpenProtocol {
    var variable: Object { get set }
    func method(param: Object, closure: () -> ()) -> Object
}
