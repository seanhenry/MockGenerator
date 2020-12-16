convenience init() {
self.init(a: 0)
}
var invokedAGetter = false
var invokedAGetterCount = 0
var stubbedA: Int! = 0
override var a: Int {
invokedAGetter = true
invokedAGetterCount += 1
return stubbedA
}
var invokedMethodA = false
var invokedMethodACount = 0
override func methodA() {
invokedMethodA = true
invokedMethodACount += 1
}