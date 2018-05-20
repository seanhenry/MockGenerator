var stubbedReadWrite: String! = ""
var readWrite: String {
set {
}
get {
return stubbedReadWrite
}
}
var stubbedReadOnly: Int! = 0
var readOnly: Int {
return stubbedReadOnly
}
var stubbedOptional: UInt!
var optional: UInt? {
set {
}
get {
return stubbedOptional
}
}
var stubbedUnwrapped: String!
var unwrapped: String! {
set {
}
get {
return stubbedUnwrapped
}
}
var stubbedTuple: (Int, String?)!
var tuple: (Int, String?)? {
set {
}
get {
return stubbedTuple
}
}
func method() {
}