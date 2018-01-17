# What parameters were used?
Determine what parameters were passed into a method.
### Given a protocol with a method adding 2 integers
```
protocol AddIntegers {
  func add(left: Int, right: Int)
}
```
### When the add method is called with 5 and 10
```
class InvokedParametersExample {

  func execute(_ injected: AddIntegers) {
    injected.add(left: 5, right: 10)
  }
}
```
### Then the mock can determine which parameters were used
```
class InvokedParametersExampleTest: XCTestCase {

    func testCheckInvokedParameters() {
      let mock = MockAddIntegers()
      InvokedParametersExample().execute(mock)
      XCTAssertEqual(mock.invokedAddParameters?.left, 5)
      XCTAssertEqual(mock.invokedAddParameters?.right, 10)
    }
}
```
