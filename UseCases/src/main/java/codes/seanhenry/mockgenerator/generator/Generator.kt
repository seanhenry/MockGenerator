package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.algorithms.SignatureGenerator
import codes.seanhenry.mockgenerator.entities.*

class Generator(private val view: MockView) {

  private val classes = mutableListOf<Class>()
  private val protocols = mutableListOf<Protocol>()

  fun add(c: Class) {
    if (c.superclass != null) {
      classes.add(c.superclass)
    }
    // TODO: add all superclasses
    add(c.protocols)
  }

  private fun add(protocols: List<Protocol>) {
    protocols.forEach { this.protocols.add(it) }
    protocols.forEach { it.protocols.forEach { this.protocols.add(it) } }
  }

  fun generate(): String {
    val presenter = MockViewPresenter(view)
    presenter.setClassInitialisers(getClassInitializersRemovingDuplicates())
    presenter.addClassProperties(getClassPropertiesRemovingDuplicates())
    presenter.addClassMethods(getClassMethodsRemovingDuplicates())
    presenter.addInitialisers(getInitializersRemovingDuplicates())
    presenter.addProperties(getPropertiesRemovingDuplicates())
    presenter.addMethods(getMethodsRemovingDuplicates())
    return presenter.generate()
  }
  private fun getClassInitializersRemovingDuplicates(): List<Initializer> {
    return classes.flatMap { it.initializers } // TODO: test for remove dupes
//        .distinctBy { SignatureGenerator.signature(it) }
  }

  private fun getClassPropertiesRemovingDuplicates(): List<Property> {
    return classes.flatMap { it.properties } // TODO: test for remove dupes
//        .distinctBy { SignatureGenerator.signature(it) }
  }

  private fun getClassMethodsRemovingDuplicates(): List<Method> {
    return classes.flatMap { it.methods } // TODO: test for remove dupes
//        .distinctBy { SignatureGenerator.signature(it) }
  }

  private fun getInitializersRemovingDuplicates(): List<Initializer> {
    // TODO: filter class dupes
    return protocols.flatMap { it.initializers }
        .distinctBy { SignatureGenerator.signature(it) }
  }

  private fun getPropertiesRemovingDuplicates(): List<Property> {
    val classSignatures = classes.flatMap { it.properties }.map { SignatureGenerator.signature(it) }
    return protocols.flatMap { it.properties }
        .filter { !classSignatures.contains(SignatureGenerator.signature(it)) }
        .distinctBy { SignatureGenerator.signature(it) }
  }

  private fun getMethodsRemovingDuplicates(): List<Method> {
    val classSignatures = classes.flatMap { it.methods }.map { SignatureGenerator.signature(it) }
    return protocols.flatMap { it.methods }
        .filter { !classSignatures.contains(SignatureGenerator.signature(it)) }
        .distinctBy { SignatureGenerator.signature(it) }
  }
}
