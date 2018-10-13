convenience init() {
self.init(b: 0)
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
var invokedBGetter = false
var invokedBGetterCount = 0
var stubbedB: Int! = 0
var forwardToOriginalB = false
override var b: Int {
invokedBGetter = true
invokedBGetterCount += 1
if forwardToOriginalB {
super.b
}
return stubbedB
}
var invokedMethodA = false
var invokedMethodACount = 0
var forwardToOriginalMethodA = false
override func methodA() {
invokedMethodA = true
invokedMethodACount += 1
if forwardToOriginalMethodA {
super.methodA()
}
}
var invokedMethodB = false
var invokedMethodBCount = 0
var forwardToOriginalMethodB = false
override func methodB() {
invokedMethodB = true
invokedMethodBCount += 1
if forwardToOriginalMethodB {
super.methodB()
}
}