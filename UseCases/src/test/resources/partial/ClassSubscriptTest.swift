var invokedSubscriptGetter = false
var invokedSubscriptGetterCount = 0
var stubbedSubscriptResult: Int! = 0
var forwardToOriginalSubscript = false
override subscript() -> Int {
invokedSubscriptGetter = true
invokedSubscriptGetterCount += 1
if forwardToOriginalSubscript {
return super[]
}
return stubbedSubscriptResult
}
var invokedSubscriptBIntGetter = false
var invokedSubscriptBIntGetterCount = 0
var invokedSubscriptBIntGetterParameters: (b: Int, Void)?
var invokedSubscriptBIntGetterParametersList = [(b: Int, Void)]()
var stubbedSubscriptBIntResult: Int! = 0
var invokedSubscriptBIntSetter = false
var invokedSubscriptBIntSetterCount = 0
var invokedSubscriptBIntSetterParameters: (b: Int, Void)?
var invokedSubscriptBIntSetterParametersList = [(b: Int, Void)]()
var invokedSubscriptBInt: Int?
var invokedSubscriptBIntList = [Int]()
var forwardToOriginalSubscriptBInt = false
override subscript(b: Int) -> Int {
set {
invokedSubscriptBIntSetter = true
invokedSubscriptBIntSetterCount += 1
invokedSubscriptBIntSetterParameters = (b, ())
invokedSubscriptBIntSetterParametersList.append((b, ()))
invokedSubscriptBInt = newValue
invokedSubscriptBIntList.append(newValue)
if forwardToOriginalSubscriptBInt {
super[b] = newValue
}
}
get {
invokedSubscriptBIntGetter = true
invokedSubscriptBIntGetterCount += 1
invokedSubscriptBIntGetterParameters = (b, ())
invokedSubscriptBIntGetterParametersList.append((b, ()))
if forwardToOriginalSubscriptBInt {
return super[b]
}
return stubbedSubscriptBIntResult
}
}
var invokedSubscriptBStringGetter = false
var invokedSubscriptBStringGetterCount = 0
var invokedSubscriptBStringGetterParameters: (b: String, Void)?
var invokedSubscriptBStringGetterParametersList = [(b: String, Void)]()
var stubbedSubscriptBStringResult: Int! = 0
var invokedSubscriptBStringSetter = false
var invokedSubscriptBStringSetterCount = 0
var invokedSubscriptBStringSetterParameters: (b: String, Void)?
var invokedSubscriptBStringSetterParametersList = [(b: String, Void)]()
var invokedSubscriptBString: Int?
var invokedSubscriptBStringList = [Int]()
var forwardToOriginalSubscriptBString = false
override subscript(b b: String) -> Int {
set {
invokedSubscriptBStringSetter = true
invokedSubscriptBStringSetterCount += 1
invokedSubscriptBStringSetterParameters = (b, ())
invokedSubscriptBStringSetterParametersList.append((b, ()))
invokedSubscriptBString = newValue
invokedSubscriptBStringList.append(newValue)
if forwardToOriginalSubscriptBString {
super[b: b] = newValue
}
}
get {
invokedSubscriptBStringGetter = true
invokedSubscriptBStringGetterCount += 1
invokedSubscriptBStringGetterParameters = (b, ())
invokedSubscriptBStringGetterParametersList.append((b, ()))
if forwardToOriginalSubscriptBString {
return super[b: b]
}
return stubbedSubscriptBStringResult
}
}