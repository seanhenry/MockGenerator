protocol PublicProtocol {
    var variable: Object { get set }
    func method(param: String, closure: () -> ()) -> Object
}
