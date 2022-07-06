var invokedSubscriptGetter = false
var invokedSubscriptGetterCount = 0
var stubbedSubscriptResult: A!
subscript() -> A {
invokedSubscriptGetter = true
invokedSubscriptGetterCount += 1
return stubbedSubscriptResult
}