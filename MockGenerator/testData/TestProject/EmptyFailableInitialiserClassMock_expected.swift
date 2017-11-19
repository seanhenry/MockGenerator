@testable import MockableTypes

class EmptyFailableInitialiserClassMock: EmptyFailableInitialiserClass {

    override init() {
        super.init()!
    }
}
