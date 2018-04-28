protocol MultiAssociatedTypeProtocol: AssociatedTypeProtocol {

    associatedtype AnotherType
    associatedtype SomeType
    func doSomethingElse(with: SomeType!) -> AnotherType?
}
