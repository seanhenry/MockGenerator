@testable import MockableTypes

class ArgumentInitialiserClassMock: ArgumentInitialiserClass {

    convenience init() {
        self.init(a: 0, b: "", nil)
    }
}
