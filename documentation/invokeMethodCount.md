# How many times was a method invoked?
Create stricter tests by determining the exact number of times a method was called.
### Given a simple protocol
```
protocol SimpleProtocol {
  func myMethod()
}
```
### When a method is called
```
class CallMethodOnceExample {

  func execute(_ injected: SimpleProtocol) {
    injected.myMethod()
    injected.myMethod()
  }
}
```
### Then the mock can determine if the method was invoked
```
class CallMethodOnceExampleTest: XCTestCase {

    func testCheckInvocation() {
      let mock = MockSimpleProtocol()
      CallMethodOnceExample().execute(mock)
      XCTAssertTrue(mock.invokedMyMethod)
    }
}
```
