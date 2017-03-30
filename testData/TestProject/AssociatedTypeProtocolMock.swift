protocol SingleAssociated {

    associatedtype SomeType
    func doSomething(with: SomeType) -> SomeType
}

class Mock: SingleAssociated {
<caret>
}
