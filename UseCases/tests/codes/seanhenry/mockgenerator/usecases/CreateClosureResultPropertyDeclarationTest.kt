package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Closure
import junit.framework.TestCase
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CreateClosureResultPropertyDeclarationTest: TestCase() {

  fun testShouldNotCreatePropertyForClosureWithoutArguments() {
    val closure = Closure("closure", emptyList(), "", false)
    assertNull(CreateClosureResultPropertyDeclaration().transform("methodName", closure))
  }

  fun testShouldTransformName() {
    val property = CreateClosureResultPropertyDeclaration().transform("methodName", getClosureWithArgument())
    assertEquals("stubbedMethodNameClosureResult", property?.name)
  }

  fun testShouldTransformDifferentName() {
    val property = CreateClosureResultPropertyDeclaration().transform("methodName", Closure("differentClosure", listOf("Type0"), "", false))
    assertEquals("stubbedMethodNameDifferentClosureResult", property?.name)
  }

  fun testShouldTransformType() {
    val property = CreateClosureResultPropertyDeclaration().transform("methodName", getClosureWithArgument())
    assertEquals("(Type0, Void)", property?.type)
  }

  fun testShouldTransformTypes() {
    val property = CreateClosureResultPropertyDeclaration().transform("methodName", getClosureWithArguments())
    assertEquals("(Type0, Type1)", property?.type)
  }

  private fun getClosureWithArgument(): Closure {
    return Closure("closure", listOf("Type0"), "", false)
  }

  private fun getClosureWithArguments(): Closure {
    return Closure("closure", listOf("Type0", "Type1"), "", false)
  }
}
