required init(i1: Int) {
}
required init(i2: Int) {
}
required init(i3: Int) {
}
var invokedP1Getter = false
var invokedP1GetterCount = 0
var stubbedP1: Int! = 0
var p1: Int {
invokedP1Getter = true
invokedP1GetterCount += 1
return stubbedP1
}
var invokedP2Getter = false
var invokedP2GetterCount = 0
var stubbedP2: Int! = 0
var p2: Int {
invokedP2Getter = true
invokedP2GetterCount += 1
return stubbedP2
}
var invokedP3Getter = false
var invokedP3GetterCount = 0
var stubbedP3: Int! = 0
var p3: Int {
invokedP3Getter = true
invokedP3GetterCount += 1
return stubbedP3
}
var invokedM1 = false
var invokedM1Count = 0
func m1() {
invokedM1 = true
invokedM1Count += 1
}
var invokedM2 = false
var invokedM2Count = 0
func m2() {
invokedM2 = true
invokedM2Count += 1
}
var invokedM3 = false
var invokedM3Count = 0
func m3() {
invokedM3 = true
invokedM3Count += 1
}