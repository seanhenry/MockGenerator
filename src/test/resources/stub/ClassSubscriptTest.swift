var stubbedSubscriptResult: Int! = 0
override subscript() -> Int {
return stubbedSubscriptResult
}
var stubbedSubscriptBIntResult: Int! = 0
override subscript(b: Int) -> Int {
set {
}
get {
return stubbedSubscriptBIntResult
}
}
var stubbedSubscriptBStringResult: Int! = 0
override subscript(b b: String) -> Int {
set {
}
get {
return stubbedSubscriptBStringResult
}
}