package codes.seanhenry.mockgenerator.xcode

import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import codes.seanhenry.mockgenerator.entities.ProtocolProperty
import junit.framework.TestCase

class XcodeMockGeneratorTest: TestCase() {

  lateinit var generator: XcodeMockGenerator

  override fun setUp() {
    super.setUp()
    generator = XcodeMockGenerator()
  }

  fun testShouldReturnEmptyString_whenNothingToMock() {
    assertMockEquals("")
  }

  fun testShouldReturnMockedProtocol_whenSingleMethod() {
    add(
        ProtocolMethod("methodName", null, "", "func methodName()")
    )
    val expected = """
      var invokedMethodName = false
      var invokedMethodNameCount = 0
      func methodName() {
      invokedMethodName = true
      invokedMethodNameCount += 1
      }
      """.trimIndent()
    assertMockEquals(expected)
  }

  fun testShouldReturnMockedProtocol_whenMultipleMethods() {
    add(
        ProtocolMethod("methodName1", null, "", "func methodName1()"),
        ProtocolMethod("methodName2", null, "", "func methodName2()")
    )
    val expected = """
      var invokedMethodName1 = false
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
      }
      """.trimIndent()
    assertMockEquals(expected)
  }

  fun testShouldCatchInvokedParameters() {
    add(
        ProtocolMethod("methodName", "String", "label name: Type, _ name2: Type2", "func methodName(label name: Type, _ name2: Type2) -> String")
    )
    val expected = """
      var invokedMethodName = false
      var invokedMethodNameCount = 0
      var invokedMethodNameParameters: (name: Type, name2: Type2)?
      var invokedMethodNameParametersList = [(name: Type, name2: Type2)]()
      var stubbedMethodNameResult: String!
      func methodName(label name: Type, _ name2: Type2) -> String {
      invokedMethodName = true
      invokedMethodNameCount += 1
      invokedMethodNameParameters = (name, name2)
      invokedMethodNameParametersList.append((name, name2))
      return stubbedMethodNameResult
      }
      """.trimIndent()
    assertMockEquals(expected)
  }

  fun testShouldMockReadWriteProperties() {
    add(
        ProtocolProperty("readWrite", "String", true, "var readWrite: String { set get }")
    )
    val expected = """
      var invokedReadWriteSetter = false
      var invokedReadWriteSetterCount = 0
      var invokedReadWriteGetter = false
      var invokedReadWriteGetterCount = 0
      var stubbedReadWrite: String!
      var readWrite: String {
      set {
      invokedReadWriteSetter = true
      invokedReadWriteSetterCount += 1
      }
      get {
      invokedReadWriteGetter = true
      invokedReadWriteGetterCount += 1
      return stubbedReadWrite
      }
      }
    """.trimIndent()
    assertMockEquals(expected)
  }

  fun testShouldMockReadOnlyProperties() {
    add(
        ProtocolProperty("readOnly", "Int", false, "var readOnly: Int { get }")
    )
    val expected = """
    var invokedReadOnlyGetter = false
    var invokedReadOnlyGetterCount = 0
    var stubbedReadOnly: Int!
    var readOnly: Int {
    invokedReadOnlyGetter = true
    invokedReadOnlyGetterCount += 1
    return stubbedReadOnly
    }
    """.trimIndent()
    assertMockEquals(expected)
  }

  private fun add(vararg methods: ProtocolMethod) {
    methods.forEach { generator.add(it) }
  }

  private fun add(vararg properties: ProtocolProperty) {
    properties.forEach { generator.add(it) }
  }

  private fun assertMockEquals(expected: String) = assertEquals(expected, generator.generate())
}
