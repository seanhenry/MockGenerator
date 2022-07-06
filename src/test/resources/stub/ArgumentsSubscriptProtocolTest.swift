var stubbedSubscriptResult: Int! = 0
subscript(a: Int) -> Int {
set {
}
get {
return stubbedSubscriptResult
}
}