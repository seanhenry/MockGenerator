import XCTest

class MockInvocationProtocol: SimpleProtocol {

    var invokedMyMethod = false
    var invokedMyMethodCount = 0

    func myMethod() {
        invokedMyMethod = true
        invokedMyMethodCount += 1
    }
}

//: Given a simple protocol

protocol SimpleProtocol {
    func myMethod()
}

//: When a method is called

class CallMethodOnceExample {

    func execute(_ injected: SimpleProtocol) {
        injected.myMethod()
    }
}

//: Then the mock can determine if the method was invoked

class CallMethodOnceExampleTest: XCTestCase {

    func testCheckInvocation() {
        let mock = MockInvocationProtocol()
        CallMethodOnceExample().execute(mock)
        XCTAssertTrue(mock.invokedMyMethod)
    }
}


























//: When a method is called twice

class CallMethodTwiceExample {

    func execute(_ injected: SimpleProtocol) {
        injected.myMethod()
        injected.myMethod()
    }
}

//: Then the mock can determine exactly how many times the method was invoked

class CallMethodTwiceExampleTest: XCTestCase {

    func testCheckInvocation() {
        let mock = MockInvocationProtocol()
        CallMethodTwiceExample().execute(mock)
        XCTAssertEqual(mock.invokedMyMethodCount, 2)
    }
}

//: ALTERNATIVE

let callMethodTwice: (SimpleProtocol) -> () = { invocationProtocol in
    invocationProtocol.myMethod()
    invocationProtocol.myMethod()
}

class CallMethodTwiceExample2Test: XCTestCase {

    func testCheckInvocation() {
        let mock = MockInvocationProtocol()
        callMethodTwice(mock)
        XCTAssertEqual(mock.invokedMyMethodCount, 2)
    }
}
