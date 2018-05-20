var invokedGeneric1 = false
var invokedGeneric1Count = 0
var stubbedGeneric1Result: Any!
func generic1<T>() -> T {
invokedGeneric1 = true
invokedGeneric1Count += 1
return stubbedGeneric1Result as! T
}
var invokedGeneric2 = false
var invokedGeneric2Count = 0
var stubbedGeneric2Result: Any!
func generic2<T>() -> T? {
invokedGeneric2 = true
invokedGeneric2Count += 1
return stubbedGeneric2Result as? T
}
var invokedGeneric3 = false
var invokedGeneric3Count = 0
var stubbedGeneric3Result: Any!
func generic3<T>() -> T! {
invokedGeneric3 = true
invokedGeneric3Count += 1
return stubbedGeneric3Result as? T
}
var invokedGenericArray = false
var invokedGenericArrayCount = 0
var stubbedGenericArrayResult: [Any]! = []
func genericArray<T>() -> [T] {
invokedGenericArray = true
invokedGenericArrayCount += 1
return stubbedGenericArrayResult as! [T]
}