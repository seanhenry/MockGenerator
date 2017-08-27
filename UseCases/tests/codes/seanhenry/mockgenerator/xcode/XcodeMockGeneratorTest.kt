package codes.seanhenry.mockgenerator.xcode

import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import junit.framework.TestCase

class XcodeMockGeneratorTest: TestCase() {

  fun testShouldReturnEmptyString_whenNothingToMock() {
    val generator = XcodeMockGenerator()
    assertTrue(generator.generate().isEmpty())
  }

  fun testShouldReturnMockedProtocol_whenSingleMethod() {
    val generator = XcodeMockGenerator()
    val method = ProtocolMethod("methodName")
    generator.add(method)
    val expected = """var invokedMethodName = false
var invokedMethodNameCount = 0
func methodName() {
invokedMethodName = true
invokedMethodNameCount += 1
}"""
    assertEquals(expected, generator.generate())
  }

  fun testShouldReturnMockedProtocol_whenMultipleMethods() {
    val generator = XcodeMockGenerator()
    val method1 = ProtocolMethod("methodName1")
    val method2 = ProtocolMethod("methodName2")
    generator.add(method1)
    generator.add(method2)
    val expected = """var invokedMethodName1 = false
var invokedMethodName1Count = 0
func methodName1() {
invokedMethodName1 = true
invokedMethodName1Count += 1
}
var invokedMethodName2 = false
var invokedMethodName2Count = 0
func methodName2() {
invokedMethodName2 = true
invokedMethodName2Count += 1
}"""
    assertEquals(expected, generator.generate())
  }
}
