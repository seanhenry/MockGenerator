package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.entities.Protocol

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
    protocols.forEach { presenter.addInitialisers(it.initializers) }
    protocols.forEach { presenter.addProperties(it.properties) }
    protocols.forEach { presenter.addMethods(it.methods) }
    return presenter.generate()
  }
}
