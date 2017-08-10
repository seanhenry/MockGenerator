import XCTest
@testable import MockableTypes

class KeywordsTest: XCTestCase {

    func test_keywords() {
        let keywordsMock = KeywordsMock()
        keywordsMock.run(for: 1)
        XCTAssertEqual(keywordsMock.invokedRunParameters?.for, 1)
        XCTAssertEqual(keywordsMock.invokedRunParametersList[0].for, 1)
    }
}
