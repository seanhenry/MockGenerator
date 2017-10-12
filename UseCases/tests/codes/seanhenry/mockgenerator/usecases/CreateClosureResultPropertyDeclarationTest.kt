package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Closure
import junit.framework.TestCase
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CreateClosureResultPropertyDeclarationTest: TestCase() {

  fun testShouldNotCreatePropertyForClosureWithoutArguments() {
    val closure = Closure("closure", emptyList(), "")
    assertNull(CreateClosureResultPropertyDeclaration().transform("name", closure))
  }

  fun testShouldTransformName() {
    val property = CreateClosureResultPropertyDeclaration().transform("closure", getClosureWithArgument())
    assertEquals("stubbedClosureClosureResult", property?.name)
  }

  fun testShouldTransformLongName() {
    val property = CreateClosureResultPropertyDeclaration().transform("closureParam", getClosureWithArgument())
    assertEquals("stubbedClosureParamClosureResult", property?.name)
  }

  fun testShouldTransformType() {
    val property = CreateClosureResultPropertyDeclaration().transform("closure", getClosureWithArgument())
    assertEquals("(Type0, Void)", property?.type)
  }

  fun testShouldTransformTypes() {
    val property = CreateClosureResultPropertyDeclaration().transform("closure", getClosureWithArguments())
    assertEquals("(Type0, Type1)", property?.type)
  }

  private fun getClosureWithArgument(): Closure {
    return Closure("", listOf("Type0"), "")
  }

  private fun getClosureWithArguments(): Closure {
    return Closure("", listOf("Type0", "Type1"), "")
  }
}
