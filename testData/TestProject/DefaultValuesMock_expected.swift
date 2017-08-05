@testable import MockableTypes

class DefaultValuesMock: DefaultValues {

    var invokedInt = false
    var invokedIntCount = 0
    var stubbedIntResult: Int! = 0

    func int() -> Int {
        invokedInt = true
        invokedIntCount += 1
        return stubbedIntResult
    }
}
