package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.ast.TypeIdentifier

class ClosureHelper {
  companion object {
    fun createClosure(args: String, rtn: String): List<ClosureParameter> {
      return createClosure("closure", args, rtn, false)
    }

    fun createClosure(args: String, rtn: String, throws: Boolean): List<ClosureParameter> {
      return createClosure("closure", args, rtn, throws)
    }

    fun createClosure(name: String, args: String, rtn: String): List<ClosureParameter> {
      return createClosure(name, args, rtn, false)
    }

    fun createClosure(name: String, args: String, rtn: String, throws: Boolean): List<ClosureParameter> {
      var throwsToken = ""
      if (throws) {
        throwsToken = "throws"
      }
      val typeText = "($args) $throwsToken -> $rtn"
      return createClosure(name, args, rtn, typeText, false, throws)
    }

    fun createOptionalClosure(name: String, args: String, rtn: String): List<ClosureParameter> {
      val typeText = "(($args) -> $rtn)?"
      return createClosure(name, args, rtn, typeText, true, false)
    }

    fun createClosure(name: String, args: String, rtn: String, typeText: String, isOptional: Boolean, throws: Boolean): List<ClosureParameter> {
      return listOf(ClosureParameter(
          name,
          name,
          typeText,
          TypeIdentifier(typeText),
          "$name: $typeText",
          TupleUtil.getParameters(args),
          rtn,
          isOptional,
          throws
      ))
    }
  }
}
