package codes.seanhenry.mockgenerator.usecases

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
    val property = stub.transform("name", "Type")
    assertEquals("stubbedNameResult", property.name)
  }

  fun testTransformsLongName() {
    val property = stub.transform("longName", "Type")
    assertEquals("stubbedLongNameResult", property.name)
  }

  fun testTransformsTypeToIUO() {
    val property = stub.transform("name", "Type")
    assertEquals("Type!", property.type)
  }

  fun testTransformsOptionalTypeToIUO() {
    val property = stub.transform("name", "String?")
    assertEquals("String!", property.type)
  }

  fun testDoesNotTransformIUO() {
    val property = stub.transform("name", "Type!")
    assertEquals("Type!", property.type)
  }

  fun testTransformsDoubleOptionalToIUO() {
    val property = stub.transform("name", "Type??")
    assertEquals("Type!", property.type)
  }

  fun testTransformsDoubleIUOToIUO() {
    val property = stub.transform("name", "Type!!")
    assertEquals("Type!", property.type)
  }

  fun testTransformsIUOOptionalToIUO() {
    val property = stub.transform("name", "Type!?")
    assertEquals("Type!", property.type)
  }

  fun testSurroundsClosureWithBrackets() {
    val property = stub.transform("name", "() -> ()")
    assertEquals("(() -> ())!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded() {
    val property = stub.transform("name", "(() -> ())")
    assertEquals("(() -> ())!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andWhitespace() {
    val property = stub.transform("name", "(  () -> ()  )")
    assertEquals("(  () -> ()  )!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andArgument() {
    val property = stub.transform("name", "((String) -> Void)")
    assertEquals("((String) -> Void)!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andReturnValueWithNoBrackets() {
    val property = stub.transform("name", "(() -> Void)")
    assertEquals("(() -> Void)!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andReturnValueWithBrackets() {
    val property = stub.transform("name", "(() -> (Void))")
    assertEquals("(() -> (Void))!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andClosureAsArgument() {
    val property = stub.transform("name", "((() -> ()) -> (Void))")
    assertEquals("((() -> ()) -> (Void))!", property.type)
  }

  fun testDoNotSurroundClosureWithBrackets_whenAlreadySurrounded_andClosureAsReturnType() {
    val property = stub.transform("name", "(() -> () -> ())")
    assertEquals("(() -> () -> ())!", property.type)
  }
}
