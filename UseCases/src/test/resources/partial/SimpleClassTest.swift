var invokedPropertySetter = false
var invokedPropertySetterCount = 0
var invokedProperty: String?
var invokedPropertyList = [String?]()
var invokedPropertyGetter = false
var invokedPropertyGetterCount = 0
var stubbedProperty: String!
var forwardToOriginalProperty = false
override var property: String? {
set {
invokedPropertySetter = true
invokedPropertySetterCount += 1
invokedProperty = newValue
invokedPropertyList.append(newValue)
if forwardToOriginalProperty {
super.property = newValue
}
}
get {
invokedPropertyGetter = true
invokedPropertyGetterCount += 1
if forwardToOriginalProperty {
return super.property
}
return stubbedProperty
}
}
var invokedMethod = false
var invokedMethodCount = 0
var forwardToOriginalMethod = false
override func method() {
invokedMethod = true
invokedMethodCount += 1
if forwardToOriginalMethod {
super.method()
}
}
var invokedAnotherMethod = false
var invokedAnotherMethodCount = 0
var forwardToOriginalAnotherMethod = false
override func anotherMethod() {
invokedAnotherMethod = true
invokedAnotherMethodCount += 1
if forwardToOriginalAnotherMethod {
super.anotherMethod()
}
}