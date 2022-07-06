var stubbedSubscriptResult: Int! = 0
override subscript() -> Int {
set {
}
get {
return stubbedSubscriptResult
}
}
var stubbedSubscriptBResult: Int! = 0
override subscript(b: Int) -> Int {
set {
}
get {
return stubbedSubscriptBResult
}
}