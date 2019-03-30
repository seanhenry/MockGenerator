open var variable: Object {
set {
}
get {
fatalError("Dummy implementation")
}
}
open subscript(a: Int) -> Int {
set {
}
get {
return 0
}
}
open func method(param: Object, closure: () -> ()) -> Object {
fatalError("Dummy implementation")
}