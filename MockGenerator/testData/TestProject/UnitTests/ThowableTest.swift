@testable import MockableTypes
import XCTest

class ThrowableTest: XCTestCase {

    enum MyError: Error {
        case error
    }

    func testProtocolShouldThrow_whenStubbingError() {
        let mock = ThrowingProtocolMock()
        mock.stubbedThrowingMethodError = MyError.error
        XCTAssertThrowsError(try mock.throwingMethod())
    }

    func testProtocolShouldNotThrow_whenNotStubbingAnything() {
        let mock = ThrowingProtocolMock()
        try! mock.throwingMethod()
    }

    func testClassShouldThrow_whenStubbingError() {
        let mock = ThrowingClassMock()
        mock.stubbedThrowingMethodError = MyError.error
        XCTAssertThrowsError(try mock.throwingMethod())
    }

    func testClassShouldNotThrow_whenNotStubbingAnything() {
        let mock = ThrowingClassMock()
        try! mock.throwingMethod()
    }
}
