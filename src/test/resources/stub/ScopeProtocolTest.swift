open var stubbedVariable: Object!
open var variable: Object {
set {
}
get {
return stubbedVariable
}
}
open var stubbedSubscriptResult: Int! = 0
open subscript(a: Int) -> Int {
set {
}
get {
return stubbedSubscriptResult
}
}
open var stubbedMethodResult: Object!
open func method(param: Object, closure: () -> ()) -> Object {
return stubbedMethodResult
}