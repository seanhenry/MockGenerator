@testable import MockableTypes

class FailableInitialiserClassMock: FailableInitialiserClass {

    convenience init() {
        self.init(a: "")!
    }
}
