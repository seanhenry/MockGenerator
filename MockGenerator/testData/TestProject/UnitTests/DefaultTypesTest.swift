import XCTest
@testable import MockableTypes

class DefaultValuesTest: XCTestCase {

    func testIntegerConvertibles() {
        let mock = DefaultValuesMock()
        XCTAssertEqual(mock.double(), 0)
        XCTAssertEqual(mock.float(), 0)
        XCTAssertEqual(mock.int(), 0)
        XCTAssertEqual(mock.int16(), 0)
        XCTAssertEqual(mock.int32(), 0)
        XCTAssertEqual(mock.int64(), 0)
        XCTAssertEqual(mock.int8(), 0)
        XCTAssertEqual(mock.uInt(), 0)
        XCTAssertEqual(mock.uInt16(), 0)
        XCTAssertEqual(mock.uInt32(), 0)
        XCTAssertEqual(mock.uInt64(), 0)
        XCTAssertEqual(mock.uInt8(), 0)
    }

    func testArrayConvertibles() {
        let mock = DefaultValuesMock()
        XCTAssert(mock.array().isEmpty)
        XCTAssert(mock.arrayLiteral().isEmpty)
        XCTAssert(mock.arraySlice().isEmpty)
        XCTAssert(mock.contiguousArray().isEmpty)
        XCTAssert(mock.set().isEmpty)
    }

    func testDictionaryConvertibles() {
        let mock = DefaultValuesMock()
        XCTAssert(mock.dictionary().isEmpty)
        XCTAssert(mock.dictionaryShorthand().isEmpty)
        XCTAssert(mock.dictionaryLiteral().isEmpty)
    }

    func testBooleanConvertibles() {
        let mock = DefaultValuesMock()
        XCTAssertFalse(mock.bool())
    }

    func testStringConvertibles() {
        let mock = DefaultValuesMock()
        XCTAssertEqual(mock.unicodeScalar(), "!")
        XCTAssertEqual(mock.character(), "!")
        XCTAssertEqual(String(describing: mock.staticString()), "")
        XCTAssertEqual(mock.string(), "")
    }
}
