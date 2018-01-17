# How many times was a method invoked?
Create stricter tests by determining the exact number of times a method was called.
### Given a simple protocol
```
protocol SimpleProtocol {
  func myMethod()
}
```
### When a method is called twice
```
class CallMethodTwiceExample {

  func execute(_ injected: SimpleProtocol) {
    injected.myMethod()
    injected.myMethod()
  }
}
```
### Then the mock can determine exactly how many times the method was called
```
class CallMethodTwiceExampleTest: XCTestCase {

    func testCheckInvocation() {
      let mock = MockSimpleProtocol()
      CallMethodTwiceExample().execute(mock)
      XCTAssertEqual(mock.invokedMyMethodCount, 2)
    }
}
```
