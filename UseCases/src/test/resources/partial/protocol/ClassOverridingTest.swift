convenience init() {
self.init(a: 0)
}
var invokedAGetter = false
var invokedAGetterCount = 0
var stubbedA: Int! = 0
var forwardToOriginalA = false
override var a: Int {
invokedAGetter = true
invokedAGetterCount += 1
if forwardToOriginalA {
return super.a
}
return stubbedA
}
var invokedMethodA = false
var invokedMethodACount = 0
var forwardToOriginalMethodA = false
override func methodA() {
invokedMethodA = true
invokedMethodACount += 1
if forwardToOriginalMethodA {
super.methodA()
return
}
}