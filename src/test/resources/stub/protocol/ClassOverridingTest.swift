convenience init() {
self.init(a: 0)
}
var stubbedA: Int! = 0
override var a: Int {
return stubbedA
}
override func methodA() {
}