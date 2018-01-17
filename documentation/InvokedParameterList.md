# What parameters were used for multiple method calls?
The mock records the parameters passed to a method every time it is invoked.
### Given a protocol with a method adding 2 integers
```
protocol AddIntegers {
  func add(left: Int, right: Int)
}
```
### When the add method is called with 5 and 10, and then, 100 and 200
```
class InvokedParametersTwiceExample {

  func execute(_ injected: AddIntegers) {
    injected.add(left: 5, right: 10)
    injected.add(left: 100, right: 200)
  }
}
```
### Then the mock can determine exactly which parameters were used, and in the invoked order
```
class InvokedParametersTwiceExampleTest: XCTestCase {

    func testCheckInvokedParameterList() {
      let mock = MockAddIntegers()
      InvokedParametersTwiceExample().execute(mock)
      XCTAssertEqual(mock.invokedAddParameterList[0].left, 5)
      XCTAssertEqual(mock.invokedAddParameterList[0].right, 10)
      XCTAssertEqual(mock.invokedAddParameterList[1].left, 100)
      XCTAssertEqual(mock.invokedAddParameterList[1].right, 200)
    }
}
```
