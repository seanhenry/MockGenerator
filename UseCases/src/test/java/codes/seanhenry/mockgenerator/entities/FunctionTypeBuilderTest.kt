package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.ast.FunctionType
import codes.seanhenry.mockgenerator.ast.OptionalType
import codes.seanhenry.mockgenerator.ast.Type
import junit.framework.TestCase

class FunctionTypeBuilderTest: TestCase() {

  fun testBuildEmptyClosure() {
    val type = FunctionType.Builder().build()
    assert(type.parameters.isEmpty())
    assertFalse(type.throws)
    assertEquals(Type.EMPTY_TUPLE, type.returnType)
    assertEquals("() -> ()", type.text)
  }

  fun testThrows() {
    val type = FunctionType.Builder().throws().build()
    assertTrue(type.throws)
    assertEquals("() throws -> ()", type.text)
  }

  fun testArgument() {
    val type = FunctionType.Builder().argument("Type").build()
    assertEquals("Type", type.parameters[0].text)
  }

  fun testArguments() {
    val type = FunctionType.Builder()
        .argument("Type")
        .argument().optional { it.type("Type2") }
        .build()
    assertEquals("Type", type.parameters[0].text)
    assertEquals("Type2?", type.parameters[1].text)
    assertEquals("(Type, Type2?) -> ()", type.text)
  }

  fun testReturnType() {
    val type = FunctionType.Builder()
        .returnType("Type")
        .build()
    assertEquals("() -> Type", type.text)
    assertEquals("Type", type.returnType.text)
  }

  fun testOptionalArgument() {
    val type = FunctionType.Builder()
        .argument().optional { opt -> opt.type("Type") }
        .build()
    assertEquals("(Type?) -> ()", type.text)
    assertEquals("Type?", type.parameters[0].text)
    assertEquals("()", type.returnType.text)
  }

  fun testArrayArgument() {
    val type = FunctionType.Builder()
        .argument().array { }
        .build()
    assertEquals("([]) -> ()", type.text)
    assertEquals("[]", type.parameters[0].text)
    assertEquals("()", type.returnType.text)
  }
}
