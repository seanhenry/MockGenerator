var stubbedGeneric1Result: Any!
func generic1<T>() -> T {
return stubbedGeneric1Result as! T
}
var stubbedGeneric2Result: Any!
func generic2<T>() -> T? {
return stubbedGeneric2Result as? T
}
var stubbedGeneric3Result: Any!
func generic3<T>() -> T! {
return stubbedGeneric3Result as? T
}
var stubbedGenericArrayResult: [Any]! = []
func genericArray<T>() -> [T] {
return stubbedGenericArrayResult as! [T]
}