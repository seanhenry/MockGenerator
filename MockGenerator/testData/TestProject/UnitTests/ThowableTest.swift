@testable import MockableTypes
import XCTest

class ThrowableTest: XCTestCase {

    enum MyError: Error {
        case error
    }

    func testShouldThrow_whenStubbingError() {
        let mock = ThrowingProtocolMock()
        mock.stubbedThrowingMethodError = MyError.error
        XCTAssertThrowsError(try mock.throwingMethod())
    }

    func testShouldNotThrow_whenNotStubbingAnything() {
        let mock = ThrowingProtocolMock()
        try! mock.throwingMethod()
    }
}
