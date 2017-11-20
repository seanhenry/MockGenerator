import XCTest

class UnitTests: XCTestCase {
    
    func testInvokedMethod() {
        let mock = SimpleProtocolMock()
        XCTAssertFalse(mock.invokedAnotherMethod)
        mock.anotherMethod(var1: "", var2: 0, var3: 0)
        XCTAssert(mock.invokedAnotherMethod)
    }

    func testInvokedParameters() {
        let mock = SimpleProtocolMock()
        XCTAssertNil(mock.invokedAnotherMethodParameters)
        mock.anotherMethod(var1: "a", var2: 1, var3: 2)
        XCTAssertEqual(mock.invokedAnotherMethodParameters?.var1, "a")
        XCTAssertEqual(mock.invokedAnotherMethodParameters?.var2, 1)
        XCTAssertEqual(mock.invokedAnotherMethodParameters?.var3, 2)
        XCTAssertEqual(mock.invokedAnotherMethodParametersList[0].var1, "a")
        XCTAssertEqual(mock.invokedAnotherMethodParametersList[0].var2, 1)
        XCTAssertEqual(mock.invokedAnotherMethodParametersList[0].var3, 2)
        XCTAssertEqual(mock.invokedAnotherMethodCount, 1)
    }

    func testInvokedParametersList() {
        let mock = SimpleProtocolMock()
        XCTAssertEqual(mock.invokedAnotherMethodParametersList.count, 0)
        mock.anotherMethod(var1: "a", var2: 1, var3: 2)
        mock.anotherMethod(var1: "b", var2: 11, var3: 21)
        XCTAssertEqual(mock.invokedAnotherMethodParametersList[0].var1, "a")
        XCTAssertEqual(mock.invokedAnotherMethodParametersList[0].var2, 1)
        XCTAssertEqual(mock.invokedAnotherMethodParametersList[0].var3, 2)
        XCTAssertEqual(mock.invokedAnotherMethodParametersList[1].var1, "b")
        XCTAssertEqual(mock.invokedAnotherMethodParametersList[1].var2, 11)
        XCTAssertEqual(mock.invokedAnotherMethodParametersList[1].var3, 21)
        XCTAssertEqual(mock.invokedAnotherMethodParameters?.var1, "b")
        XCTAssertEqual(mock.invokedAnotherMethodParameters?.var2, 11)
        XCTAssertEqual(mock.invokedAnotherMethodParameters?.var3, 21)
        XCTAssertEqual(mock.invokedAnotherMethodCount, 2)
    }

    func testClosuresWithNoArgumentsAreCalledAutomatically() {
        let mock = MockClosureProtocol()
        var invokedMap = false
        mock.map {
            invokedMap = true
        }
        XCTAssert(invokedMap)
    }

    func testClosuresWithArgumentsAreNotCalledAutomatically() {
        let mock = MockClosureProtocol()
        var invokedString: String?
        mock.filter { string in
            invokedString = string
            return false
        }
        XCTAssertNil(invokedString)
    }

    func testClosuresWithStubbedArgumentsAreCalled() {
        let mock = MockClosureProtocol()
        var invokedString: String?
        mock.stubbedFilterClosureResult = ("hi", ())
        mock.filter { string in
            invokedString = string
            return false
        }
        XCTAssertEqual(invokedString, "hi")
    }
}
