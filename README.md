> ⚠️ This plugin is no longer being actively worked on as the author is no longer writing much Swift. Updating AppCode can cause breaking changes from time to time so feel free to raise an issue or make a pull request and I will try to fix the problem. Thank you everyone for your support!

# Swift Mock Generator for AppCode

An AppCode plugin to generate spy, stub and dummy classes automatically. 

![AppCode plugin generates Swift spy](readme/MockGenerator.gif "AppCode plugin generates Swift mock")

[Looking for the Xcode version?](https://github.com/seanhenry/SwiftMockGeneratorForXcode)

## Install Swift Mock Generator for AppCode

- Open AppCode
- Open AppCode → Preferences `⌘,`
- Go to Plugins
- Click Browse repositories...
- Search for 'Swift Mock Generator for AppCode'
- Click Install
- Restart AppCode

## How to create a new Swift test double

You can generate a spy, stub or dummy. [See here](https://martinfowler.com/bliki/TestDouble.html) for more information around test double nomenclature.

- Create an empty class conforming to one or many protocols and/or a class.
- With the cursor inside the class declaration, press `⌥↵`.
- Select 'Generate spy|stub|dummy|partial spy'.

## How to recreate a Swift test double

If you change the underlying protocol its test double will need to be regenerated.  

To regenerate the test double, place the cursor anywhere inside the test double and select ‘Generate spy|stub|dummy’ again.

## Features

| Feature                                                                                              | Supported         |
|------------------------------------------------------------------------------------------------------|-------------------|
| Generate and regenerate a spy.                                                                       | ✅                 |
| Generate and regenerate a stub.                                                                      | ✅                 |
| Generate and regenerate a dummy.                                                                     | ✅                 |
| Generate and regenerate a partial spy (forwards invocations to original implementation if required). | ✅                 |
| **Classes and protocols**                                                                            |
| Generates test doubles conforming to one or many protocols.                                          | ✅                 |
| Generates test doubles conforming to a class.                                                        | ✅                 |
| Generates test doubles conforming to both classes and protocols.                                     | ✅                 |
| **Recording methods and properties**                                                                 |
| Captures invocation status of methods.                                                               | ✅                 |
| Captures invocation status of properties.                                                            | ✅                 |
| Records multiple invocations of methods.                                                             | ✅                 |
| Records multiple invocations of properties.                                                          | ✅                 |
| Captures invoked method parameters.                                                                  | ✅                 |
| Records multiple invocations of method parameters.                                                   | ✅                 |
| Supports multiple properties in the same declaration.                                                | ✅                 |
| **Stubbing return values and closures**                                                              |
| Stubs values for your test doubles to return.                                                        | ✅                 |
| Stubs a default value for return values where possible.                                              | ✅                 |
| Automatically calls closure parameters with stubbed values.                                          | ✅                 |
| **Initializers**                                                                                     |
| Generates convenience initializers requiring no parameters.                                          | ✅                 |
| Supports initializers with arguments.                                                                | ✅                 |
| Supports failable initializers.                                                                      | ✅                 |
| Supports required initializers.                                                                      | ✅                 |
| **Throws**                                                                                           |
| Stub an error for your overridden method to throw.                                                   | ✅                 |
| Supports throwing initializers.                                                                      | ✅                 |
| Supports throwing closures.                                                                          | ✅                 |
| **Generics**                                                                                         |
| Generates generic test doubles from protocols with associated types.                                 | ✅                 |
| Captures invoked generic parameters.                                                                 | ✅                 |
| Captures invoked generic return values.                                                              | ✅                 |
| **Scope, keywords, and more**                                                                        |
| Avoids naming clashes from overloaded methods.                                                       | ✅                 |
| Supports parameter type-annotation attributes and `inout`.                                           | ✅                 |
| Respects the test double scope and generates `public` and `open` methods and properties.             | ✅(protocols only) |
| Generate test doubles inheriting from items in 3rd party frameworks.                                 | ✅(protocols only) |

## Usage example

A protocol called Animator that we wish to spy on:

```
protocol Animator {
  func animate(duration: TimeInterval, animations: () -> (), completion: (Bool) -> ()) -> Bool
}
```
Create a spy class conforming to a protocol:
```
class AnimatorSpy: Animator {
}
```
Generate the spy:

```
class AnimatorSpy: Animator {

  var invokedAnimate = false
  var invokedAnimateCount = 0
  var invokedAnimateParameters: (duration: TimeInterval, Void)?
  var invokedAnimateParametersList = [(duration: TimeInterval, Void)]()
  var shouldInvokeAnimateAnimations = false
  var stubbedAnimateCompletionResult: (Bool, Void)?
  var stubbedAnimateResult: Bool! = false

  func animate(duration: TimeInterval, animations: () -> (), completion: (Bool) -> ()) -> Bool {
    invokedAnimate = true
    invokedAnimateCount += 1
    invokedAnimateParameters = (duration, ())
    invokedAnimateParametersList.append((duration, ()))
    if shouldInvokeAnimateAnimations {
      animations()
    }
    if let result = stubbedAnimateCompletionResult {
      completion(result.0)
    }
    return stubbedAnimateResult
  }
}
```
Inject the spy into the class you wish to test:

```
let animatorSpy = AnimatorSpy()
let object = ObjectToTest(animator: animatorSpy)
```
Test if animate method was invoked:

```
func test_spyCanVerifyInvokedMethod() {
  object.myMethod()
  XCTAssertTrue(animatorSpy.invokedAnimate)
}
```
Test the correct parameter was passed to the animate method:

```
func test_spyCanVerifyInvokedParameters() {
  object.myMethod()
  XCTAssertEqual(animatorSpy.invokedAnimateParameters?.duration, 5)
}
```
Test the number of times animate was invoked:

```
func test_spyCanVerifyInvokedMethodCount() {
  object.myMethod()
  object.myMethod()
  XCTAssertEqual(animatorSpy.invokedAnimateCount, 2)
}
```
Test the parameters passed into each call of the animate method:

```
func test_spyCanVerifyMultipleInvokedMethodParameters() {
  object.myMethod()
  object.myMethod()
  XCTAssertEqual(animatorSpy.invokedAnimateParametersList[0].duration, 5)
  XCTAssertEqual(animatorSpy.invokedAnimateParametersList[1].duration, 5)
}
```
Stub a return value for the animate method:

```
func test_spyCanReturnAStubbedValue() {
  animatorSpy.stubbedAnimateResult = true
  let result = object.myMethod()
  XCTAssertTrue(result)
}
```
Stub the value for the completion closure in the animate method:

```
func test_spyCanCallClosure_withStubbedValue() {
  animatorSpy.stubbedAnimateCompletionResult = (false, ())
  object.myMethod()
  XCTAssertFalse(object.animationDidComplete)
}
```

## Nomenclature

Despite being called a Mock Generator, this plugin actually generates a spy, stub or dummy. The word 'mock', whilst not technically correct, has been used because test doubles such as spies, mocks, and stubs have become commonly known as mocks.

## Build

If you would like to try building the plugin yourself follow these steps.

- Open `build.gradle`
- Change `intellij.localPath` and `runIde.ideDirectory` to the path of your AppCode app
- Run `./gradlew runIde`
