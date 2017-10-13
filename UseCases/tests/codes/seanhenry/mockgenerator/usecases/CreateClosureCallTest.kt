package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Closure
import junit.framework.TestCase
import kotlin.test.assertEquals

class CreateClosureCallTest : TestCase() {

  private lateinit var closures: List<Closure>

  fun testShouldReturnClosureWithNoArguments() {
    transform(emptyClosureParameter())
    assertClosureCount(1)
    assertClosure("closure", emptyList(), "", 0)
  }

  fun testShouldIgnoreNonClosures() {
    transform(nonClosureParameters())
    assertClosureCount(0)
  }

  fun testShouldReturnClosureWithArgument() {
    transform(argumentsClosureParameter())
    assertClosureCount(1)
    assertClosure("argClosure", listOf("String"), "", 0)
  }

  fun testShouldReturnClosureWithArguments() {
    transform(listOf("closure: (String, Int?, UInt!) -> ()"))
    assertClosureCount(1)
    assertClosure("closure", listOf("String", "Int?", "UInt!"), "", 0)
  }

  fun testShouldReturnMultipleClosureWithArguments() {
    transform(listOf("closureA: (String, Int?, UInt!) -> ()", "closureB: () -> ()", "closureC: (String) -> ()"))
    assertClosureCount(3)
    assertClosure("closureA", listOf("String", "Int?", "UInt!"), "", 0)
    assertClosure("closureB", emptyList(), "", 1)
    assertClosure("closureC", listOf("String"), "", 2)
  }

  fun testShouldReturnClosureWithWeirdWhitespace() {
    transform(listOf("  \t\n  closure   \t\n  :\t   \n    (  \t\t   \nString,\t\t\t\tInt?,\n\t  UInt!) -> (  ) \n "))
    assertClosureCount(1)
    assertClosure("closure", listOf("String", "Int?", "UInt!"), "", 0)
  }

  fun testShouldReturnClosureWithNoWhitespace() {
    transform(listOf("closure:(String,Int?,UInt!)->()"))
    assertClosureCount(1)
    assertClosure("closure", listOf("String", "Int?", "UInt!"), "", 0)
  }

  fun testShouldReturnClosureWithReturnArgument() {
    transform(listOf("closure: (String) -> (String)"))
    assertClosureCount(1)
    assertClosure("closure", listOf("String"), "String", 0)
  }

  fun testShouldReturnClosureWithReturnArgumentWithNoParentheses() {
    transform(listOf("closure: (String) -> String"))
    assertClosureCount(1)
    assertClosure("closure", listOf("String"), "String", 0)
  }

  fun testShouldReturnClosureReturningAnotherClosure() {
    transform(listOf("closure: (String) -> ((String) -> (String))"))
    assertClosureCount(1)
    assertClosure("closure", listOf("String"), "(String) -> (String)", 0)
  }

  fun testShouldReturnClosureWithLegalArgumentLabel() {
    transform(listOf("closure: (_ a: String) -> ()"))
    assertClosureCount(1)
    assertClosure("closure", listOf("String"), "", 0)
  }

  fun testShouldReturnClosureWithVoid() {
    transform(listOf("closure: ((), Void) -> (Void)"))
    assertClosureCount(1)
    assertClosure("closure", listOf("()", "Void"), "", 0)
  }

  fun testShouldNotCrashWhenNotAValidClosure() {
    transform(listOf("closure: String -> Void"))
    transform(listOf("String -> Void"))
    transform(listOf("String"))
  }

  fun testShouldReturnOptionalClosure() {
    transform(listOf("closure: ((String) -> ())?"))
    assertClosureCount(1)
    assertClosure("closure", listOf("String"), "", 0, true)
  }

  fun testShouldReturnIUOClosure() {
    transform(listOf("closure: ((String) -> ())!"))
    assertClosureCount(1)
    assertClosure("closure", listOf("String"), "", 0, true)
  }

  fun testShouldReturnOptionalClosureWithWeirdWhitespace() {
    transform(listOf("closure: ((String) -> ()  \t\n )?  \t\t\n "))
    assertClosureCount(1)
    assertClosure("closure", listOf("String"), "", 0, true)
  }

  fun testShouldReturnClosureWithOptionalValue() {
    transform(listOf("closure: (String) -> String?"))
    assertClosureCount(1)
    assertClosure("closure", listOf("String"), "String?", 0)
  }

  private fun transform(parameters: List<String>) {
    closures = CreateClosureCall().transform(parameters)
  }

  private fun assertClosureCount(expected: Int) {
    assertEquals(expected, closures.size)
  }

  private fun assertClosure(expectedName: String,
                            expectedArguments: List<String>,
                            expectedReturnValue: String,
                            index: Int,
                            expectedOptionalValue: Boolean = false) {
    val closure = closures[index]
    assertEquals(expectedName, closure.name)
    assertEquals(expectedArguments, closure.arguments)
    assertEquals(expectedReturnValue, closure.returnValue)
    assertEquals(expectedOptionalValue, closure.isOptional)
  }

  private fun argumentsClosureParameter(): List<String> {
    return listOf("argClosure: (String) -> ()")
  }

  private fun emptyClosureParameter(): List<String> {
    return listOf("closure: () -> ()")
  }

  private fun nonClosureParameters(): List<String> {
    return listOf("int: Int", "opt: Optional<Object>", "opt: String?", "iuo: UInt!")
  }
}
