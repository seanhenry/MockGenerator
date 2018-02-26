package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.GenericType
import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.entities.Type
import codes.seanhenry.mockgenerator.util.AppendStringDecorator
import codes.seanhenry.mockgenerator.util.PrependStringDecorator
import codes.seanhenry.mockgenerator.util.StringDecorator
import junit.framework.TestCase

class CreateStubTest: TestCase() {
  
  lateinit var stub: CreateStub

  override fun setUp() {
    super.setUp()
    class MyStub: CreateStub() {
      override fun getStringDecorator(): StringDecorator {
        val appendDecorator = AppendStringDecorator(null, "Result")
        return PrependStringDecorator(appendDecorator, "stubbed")
      }
    }
    stub = MyStub()
  }

  fun testTransformsName() {
    val property = transform("name", "Type")
    assertEquals("stubbedNameResult", property.name)
  }

  fun testTransformsLongName() {
    val property = transform("longName", "Type")
    assertEquals("stubbedLongNameResult", property.name)
  }

  fun testTransformsTypeToIUO() {
    val property = transform("name", "Type")
    assertEquals("Type!", property.type)
  }

  fun testTransformsOptionalTypeToIUO() {
    val property = transform("name", "String?")
    assertEquals("String!", property.type)
  }

  fun testDoesNotTransformIUO() {
    val property = transform("name", "Type!")
    assertEquals("Type!", property.type)
  }

  fun testTransformsDoubleOptionalToIUO() {
    val property = transform("name", "Type??")
    assertEquals("Type!", property.type)
  }

  fun testTransformsDoubleIUOToIUO() {
    val property = transform("name", "Type!!")
    assertEquals("Type!", property.type)
  }

  fun testTransformsIUOOptionalToIUO() {
    val property = transform("name", "Type!?")
    assertEquals("Type!", property.type)
  }

  fun testSurroundsClosureWithBrackets() {
    val property = transform("name", "() -> ()")
    assertEquals("(() -> ())!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded() {
    val property = transform("name", "(() -> ())")
    assertEquals("(() -> ())!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andWhitespace() {
    val property = transform("name", "(  () -> ()  )")
    assertEquals("(  () -> ()  )!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andArgument() {
    val property = transform("name", "((String) -> Void)")
    assertEquals("((String) -> Void)!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andReturnValueWithNoBrackets() {
    val property = transform("name", "(() -> Void)")
    assertEquals("(() -> Void)!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andReturnValueWithBrackets() {
    val property = transform("name", "(() -> (Void))")
    assertEquals("(() -> (Void))!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andClosureAsArgument() {
    val property = transform("name", "((() -> ()) -> (Void))")
    assertEquals("((() -> ()) -> (Void))!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andClosureAsReturnType() {
    val property = transform("name", "(() -> () -> ())")
    assertEquals("(() -> () -> ())!", property.type)
  }

  fun testTransformsGenericReturnTypeToAny() {
    val property = stub.transform("", "T", GenericType("T"))
    assertEquals("Any!", property.type)
  }

  fun testTransformsOptionalGenericReturnTypeToAny() {
    val property = stub.transform("", "T?", GenericType("T?"))
    assertEquals("Any!", property.type)
  }

  fun testTransformsIUOGenericReturnTypeToAny() {
    val property = stub.transform("", "T!", GenericType("T!"))
    assertEquals("Any!", property.type)
  }

  private fun transform(name: String, type: String): PropertyDeclaration {
    return stub.transform(name, type, Type(type))
  }
}
