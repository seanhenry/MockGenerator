protocol MultiAssociatedTypeProtocol {

    associatedtype SomeType
    associatedtype AnotherType
    func doSomething(with: SomeType) -> AnotherType
    func doSomethingElse(with: SomeType!) -> SomeType?
}
