package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.entities.TuplePropertyDeclaration
import codes.seanhenry.mockgenerator.algorithms.CopyVisitor
import codes.seanhenry.mockgenerator.algorithms.TypeErasingVisitor
import codes.seanhenry.mockgenerator.util.ClosureUtil
import codes.seanhenry.mockgenerator.util.StringDecorator

abstract class CreateParameterTuple {

  abstract fun getStringDecorator(): StringDecorator

  fun transform(name: String, parameterList: List<Parameter>, genericIdentifiers: List<String>): TuplePropertyDeclaration? {
    val tupleParameters = parameterList
        .mapNotNull { transformParameter(it, genericIdentifiers) }
    if (validateParameters(parameterList, tupleParameters)) {
      return createProperty(transformName(name), tupleParameters.filter { !isClosure(it) })
    }
    return null
  }

  private fun transformName(name: String): String {
    return getStringDecorator().process(name)
  }

  private fun validateParameters(parameterList: List<Any>, tupleParameters: List<Any>) =
      parameterList.size == tupleParameters.size

  private fun createProperty(name: String, tupleParameters: List<TuplePropertyDeclaration.TupleParameter>): TuplePropertyDeclaration? {
    if (tupleParameters.isEmpty()) {
      return null
    } else if (tupleParameters.size == 1) {
      val mutable = tupleParameters.toMutableList()
      mutable.add(TuplePropertyDeclaration.TupleParameter("", "Void"))
      return TuplePropertyDeclaration(name, mutable.toList())
    }
    return TuplePropertyDeclaration(name, tupleParameters)
  }

  private fun isClosure(parameter: TuplePropertyDeclaration.TupleParameter): Boolean {
    return ClosureUtil.isClosure(parameter.resolvedType)
  }

  private fun transformParameter(parameter: Parameter, genericIdentifiers: List<String>): TuplePropertyDeclaration.TupleParameter? {
    val name = parameter.name
    val copied = CopyVisitor.copy(parameter.type.originalType)
    TypeErasingVisitor.erase(copied, genericIdentifiers)
    val resolvedType = parameter.resolvedType
    return TuplePropertyDeclaration.TupleParameter(name, removeInOut(replaceIUO(copied.text)), resolvedType)
  }

  private fun replaceIUO(type: String): String {
    if (type.endsWith("!")) {
      return type.removeSuffix("!") + "?"
    }
    return type
  }

  private fun removeInOut(type: String): String {
    val inout = "inout "
    val index = type.indexOf(inout)
    if (index >= 0) {
      return type.substring(index + inout.length)
    }
    return type
  }
}
