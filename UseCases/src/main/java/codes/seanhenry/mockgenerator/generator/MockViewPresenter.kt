package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.swift.SwiftStringTupleForwardCall
import codes.seanhenry.mockgenerator.usecases.CreateInvokedParameters
import codes.seanhenry.mockgenerator.util.UniqueMethodNameGenerator

class MockViewPresenter(val view: MockView): MockTransformer {

  private val protocolMethods = ArrayList<ProtocolMethod>()
  private lateinit var nameGenerator: UniqueMethodNameGenerator

  override fun add(method: ProtocolMethod) {
    protocolMethods.add(method)
  }

  override fun add(vararg methods: ProtocolMethod) {
    addMethods(listOf(*methods))
  }

  override fun addMethods(methods: List<ProtocolMethod>) {
    for (method in methods) {
      this.protocolMethods.add(method)
    }
  }

  override fun setScope(scope: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun add(property: ProtocolProperty) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun add(vararg initialisers: Initialiser) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun add(vararg properties: ProtocolProperty) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addInitialisers(initialisers: List<Initialiser>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addProperties(properties: List<ProtocolProperty>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setClassInitialisers(vararg initialisers: Initialiser) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setClassInitialisers(initialisers: List<Initialiser>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addClassMethods(vararg methods: ProtocolMethod) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addClassMethods(methods: List<ProtocolMethod>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addClassProperties(vararg properties: ProtocolProperty) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addClassProperties(properties: List<ProtocolProperty>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun generate(): String {
    val mockModel = MockViewModel(
        transformMethods()
    )
    view.render(mockModel)
    return ""
  }

  private fun transformMethods(): List<MethodViewModel> {
    return protocolMethods.map {
      MethodViewModel(
          it.name.capitalize(),
          transformParameters(it),
          it.signature
      )
    }
  }

  private fun transformParameters(method: ProtocolMethod): ParametersViewModel? {
    if (method.parametersList.isEmpty()) {
      return null
    }
    val declaration = transformToTupleDeclaration(method.parametersList) ?: return null
    val assignment = transformToTupleAssignment(declaration) ?: return null
    return ParametersViewModel(
        declaration.type,
        assignment
    )
  }

  private fun transformToTupleDeclaration(parameters: List<Parameter>): TuplePropertyDeclaration? {
    return CreateInvokedParameters().transform("", parameters) // TODO: remove name
  }

  private fun transformToTupleAssignment(tuple: TuplePropertyDeclaration): String? {
    return SwiftStringTupleForwardCall().transform(tuple)
  }
}
