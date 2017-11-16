@testable import MockableTypes

class MultipleInitialiserClassMock: MultipleInitialiserClass {

    convenience init() {
        self.init(f: "")
    }
}
