@testable import MockableTypes

class RequiredInitialiserClassMock: RequiredInitialiserClass {

    convenience init() {
        self.init(a: "")
    }
}
