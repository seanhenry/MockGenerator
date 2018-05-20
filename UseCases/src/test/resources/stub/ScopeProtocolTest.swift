open var stubbedVariable: Object!
open var variable: Object {
set {
}
get {
return stubbedVariable
}
}
open var stubbedMethodResult: Object!
open func method(param: Object, closure: () -> ()) -> Object {
return stubbedMethodResult
}