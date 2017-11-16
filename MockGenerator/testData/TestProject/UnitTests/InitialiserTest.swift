import XCTest
@testable import MockableTypes

class InitialiserTest: XCTestCase {

    func test_noInitialiser() {
        _ = SimpleClassMock()
        _ = InferredTypeClassMock()
        _ = PropertyClassMock()
        _ = UnoverridableClassMock()
    }

    func test_initialiserWithArgs() {
        _ = ArgumentInitialiserClassMock()
    }
}
