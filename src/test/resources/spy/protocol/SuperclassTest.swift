convenience init() {
self.init(b: 0)
}
var invokedAGetter = false
var invokedAGetterCount = 0
var stubbedA: Int! = 0
override var a: Int {
invokedAGetter = true
invokedAGetterCount += 1
return stubbedA
}
var invokedBGetter = false
var invokedBGetterCount = 0
var stubbedB: Int! = 0
override var b: Int {
invokedBGetter = true
invokedBGetterCount += 1
return stubbedB
}
var invokedMethodA = false
var invokedMethodACount = 0
override func methodA() {
invokedMethodA = true
invokedMethodACount += 1
}
var invokedMethodB = false
var invokedMethodBCount = 0
override func methodB() {
invokedMethodB = true
invokedMethodBCount += 1
}