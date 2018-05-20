convenience init() {
self.init(b: 0)
}
var stubbedA: Int! = 0
override var a: Int {
return stubbedA
}
var stubbedB: Int! = 0
override var b: Int {
return stubbedB
}
override func methodA() {
}
override func methodB() {
}