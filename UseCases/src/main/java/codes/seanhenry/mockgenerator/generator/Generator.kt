package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.algorithms.SignatureGenerator
import codes.seanhenry.mockgenerator.entities.*

class Generator(private val view: MockView) {

  private val protocols = mutableListOf<Protocol>()

  fun add(protocol: Protocol) {
    protocols.add(protocol)
  }

  fun add(vararg protocol: Protocol) {
    protocols.addAll(listOf(*protocol))
  }

  fun generate(): String {
    val presenter = MockViewPresenter(view)
    presenter.addInitialisers(getInitializersRemovingDuplicates())
    presenter.addProperties(getPropertiesRemovingDuplicates())
    presenter.addMethods(getMethodsRemovingDuplicates())
    return presenter.generate()
  }

  private fun getInitializersRemovingDuplicates(): List<Initializer> {
    return protocols.flatMap { it.initializers }
        .distinctBy { SignatureGenerator.signature(it) }
  }

  private fun getPropertiesRemovingDuplicates(): List<Property> {
    return protocols.flatMap { it.properties }
        .distinctBy { SignatureGenerator.signature(it) }
  }

  private fun getMethodsRemovingDuplicates(): List<Method> {
    return protocols.flatMap { it.methods}
        .distinctBy { SignatureGenerator.signature(it) }
  }
}
