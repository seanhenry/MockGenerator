package codes.seanhenry.mockgenerator.entities

class ClosureHelper {
  companion object {
    fun createClosure(args: String, rtn: String): List<ClosureParameter> {
      return createClosure("closure", args, rtn)
    }

    fun createClosure(name: String, args: String, rtn: String): List<ClosureParameter> {
      val typeText = "($args) -> $rtn"
      return createClosure(name, args, rtn, typeText, false)
    }

    fun createOptionalClosure(name: String, args: String, rtn: String): List<ClosureParameter> {
      val typeText = "(($args) -> $rtn)?"
      return createClosure(name, args, rtn, typeText, true)
    }

    fun createClosure(name: String, args: String, rtn: String, typeText: String, isOptional: Boolean): List<ClosureParameter> {
      return listOf(ClosureParameter(
          name,
          name,
          typeText,
          Type(typeText),
          "$name: $typeText",
          TupleUtil.getParameters(args),
          rtn,
          isOptional
      ))
    }
  }
}
