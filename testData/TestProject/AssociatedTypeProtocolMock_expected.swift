protocol SingleAssociated {

    associatedtype SomeType
    func doSomething(with: SomeType) -> SomeType
}

class Mock<SomeType>: SingleAssociated {

    var invokedDoSomething = false
    var invokedDoSomethingParameters: (with: SomeType, Void)?
    var stubbedDoSomethingResult: SomeType!
    func doSomething(with: SomeType) -> SomeType {
        invokedDoSomething = true
        invokedDoSomethingParameters = (with, ())
        return stubbedDoSomethingResult
    }
}
