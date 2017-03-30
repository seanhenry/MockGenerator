protocol MultiAssociated {

    associatedtype SomeType
    associatedtype AnotherType
    func doSomething(with: SomeType) -> AnotherType
    func doSomethingElse(with: SomeType!) -> SomeType?
}

class Mock<ExistingType>: MultiAssociated {
<caret>
}
