package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.ast.*
import junit.framework.TestCase

class VisitorTest: TestCase() {

  lateinit var visitor: VisitorSpy

  override fun setUp() {
    super.setUp()
    visitor = VisitorSpy()
  }

  fun testShouldVisitType() {
    val type = Type("Type")
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
  }

  fun testShouldVisitFunctionType() {
    val type = FunctionType("type", emptyList(), Type.VOID, false)
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitFunctionType)
  }

  fun testShouldVisitOptionalType() {
    val type = OptionalType("Type?", Type("Type"), false)
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitOptionalType)
  }

  fun testShouldVisitBracketType() {
    val type = BracketType(Type("Type"))
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitBracketType)
  }

  fun testShouldVisitArrayType() {
    val type = ArrayType("[Type]", Type("Type"))
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitArrayType)
  }

  class VisitorSpy: Visitor() {

    var didVisitType = false
    override fun visitType(type: Type) {
      super.visitType(type)
      didVisitType = true
    }

    var didVisitFunctionType = false
    override fun visitFunctionType(type: FunctionType) {
      super.visitFunctionType(type)
      didVisitFunctionType = true
    }

    var didVisitOptionalType = false
    override fun visitOptionalType(type: OptionalType) {
      super.visitOptionalType(type)
      didVisitOptionalType = true
    }

    var didVisitBracketType = false
    override fun visitBracketType(type: BracketType) {
      super.visitBracketType(type)
      didVisitBracketType = true
    }

    var didVisitArrayType = false
    override fun visitArrayType(type: ArrayType) {
      super.visitArrayType(type)
      didVisitArrayType = true
    }
  }
}
