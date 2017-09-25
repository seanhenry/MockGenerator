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
      var stubbedMethodNameResult: String! = ""
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
      var invokedReadWrite: String?
      var invokedReadWriteList = [String]()
      var invokedReadWriteGetter = false
      var invokedReadWriteGetterCount = 0
      var stubbedReadWrite: String! = ""
      var readWrite: String {
      set {
      invokedReadWriteSetter = true
      invokedReadWriteSetterCount += 1
      invokedReadWrite = newValue
      invokedReadWriteList.append(newValue)
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
    var stubbedReadOnly: Int! = 0
    var readOnly: Int {
    invokedReadOnlyGetter = true
    invokedReadOnlyGetterCount += 1
    return stubbedReadOnly
    }
    """.trimIndent()
    assertMockEquals(expected)
  }

  fun testShouldMockOptionalProperties() {
    add(
        ProtocolProperty("opt", "String?", true, "var opt: String? { set get }")
    )
    add(
        ProtocolMethod("getOptional", null, "opt: Int?", "func getOptional(opt: Int?)")
    )
    val expected = """
      var invokedOptSetter = false
      var invokedOptSetterCount = 0
      var invokedOpt: String?
      var invokedOptList = [String?]()
      var invokedOptGetter = false
      var invokedOptGetterCount = 0
      var stubbedOpt: String!
      var opt: String? {
      set {
      invokedOptSetter = true
      invokedOptSetterCount += 1
      invokedOpt = newValue
      invokedOptList.append(newValue)
      }
      get {
      invokedOptGetter = true
      invokedOptGetterCount += 1
      return stubbedOpt
      }
      }
      var invokedGetOptional = false
      var invokedGetOptionalCount = 0
      var invokedGetOptionalParameters: (opt: Int?, Void)?
      var invokedGetOptionalParametersList = [(opt: Int?, Void)]()
      func getOptional(opt: Int?) {
      invokedGetOptional = true
      invokedGetOptionalCount += 1
      invokedGetOptionalParameters = (opt, ())
      invokedGetOptionalParametersList.append((opt, ()))
      }
    """.trimIndent()
    assertMockEquals(expected)
  }

  fun testShouldStubWithDefaultValues() {
    add(
        ProtocolProperty("string", "String", false, "var string: String { get }")
    )
    add(
        ProtocolMethod("getInt", "Int", "", "func getInt() -> Int")
    )
    generator.setScope("public")
    val expected = """
      public var invokedStringGetter = false
      public var invokedStringGetterCount = 0
      public var stubbedString: String! = ""
      public var string: String {
      invokedStringGetter = true
      invokedStringGetterCount += 1
      return stubbedString
      }
      public var invokedGetInt = false
      public var invokedGetIntCount = 0
      public var stubbedGetIntResult: Int! = 0
      public func getInt() -> Int {
      invokedGetInt = true
      invokedGetIntCount += 1
      return stubbedGetIntResult
      }
    """.trimIndent()
    assertMockEquals(expected)
  }

  fun testShouldAssignTheScopeToAllItems() {
    add(
        ProtocolProperty("opt", "String?", true, "var opt: String? { set get }")
    )
    add(
        ProtocolMethod("getOptional", "String?", "opt: Int?", "func getOptional(opt: Int?) -> String?")
    )
    generator.setScope("public")
    val expected = """
      public var invokedOptSetter = false
      public var invokedOptSetterCount = 0
      public var invokedOpt: String?
      public var invokedOptList = [String?]()
      public var invokedOptGetter = false
      public var invokedOptGetterCount = 0
      public var stubbedOpt: String!
      public var opt: String? {
      set {
      invokedOptSetter = true
      invokedOptSetterCount += 1
      invokedOpt = newValue
      invokedOptList.append(newValue)
      }
      get {
      invokedOptGetter = true
      invokedOptGetterCount += 1
      return stubbedOpt
      }
      }
      public var invokedGetOptional = false
      public var invokedGetOptionalCount = 0
      public var invokedGetOptionalParameters: (opt: Int?, Void)?
      public var invokedGetOptionalParametersList = [(opt: Int?, Void)]()
      public var stubbedGetOptionalResult: String!
      public func getOptional(opt: Int?) -> String? {
      invokedGetOptional = true
      invokedGetOptionalCount += 1
      invokedGetOptionalParameters = (opt, ())
      invokedGetOptionalParametersList.append((opt, ()))
      return stubbedGetOptionalResult
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
