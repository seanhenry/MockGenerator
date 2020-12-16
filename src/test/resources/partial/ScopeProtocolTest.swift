open var invokedVariableSetter = false
open var invokedVariableSetterCount = 0
open var invokedVariable: Object?
open var invokedVariableList = [Object]()
open var invokedVariableGetter = false
open var invokedVariableGetterCount = 0
open var stubbedVariable: Object!
open var variable: Object {
set {
invokedVariableSetter = true
invokedVariableSetterCount += 1
invokedVariable = newValue
invokedVariableList.append(newValue)
}
get {
invokedVariableGetter = true
invokedVariableGetterCount += 1
return stubbedVariable
}
}
open var invokedMethod = false
open var invokedMethodCount = 0
open var invokedMethodParameters: (param: Object, Void)?
open var invokedMethodParametersList = [(param: Object, Void)]()
open var shouldInvokeMethodClosure = false
open var stubbedMethodResult: Object!
open func method(param: Object, closure: () -> ()) -> Object {
invokedMethod = true
invokedMethodCount += 1
invokedMethodParameters = (param, ())
invokedMethodParametersList.append((param, ()))
if shouldInvokeMethodClosure {
closure()
}
return stubbedMethodResult
}