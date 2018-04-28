package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.algorithms.SignatureGenerator
import codes.seanhenry.mockgenerator.entities.*

class Generator(private val view: MockView) {

  private var mockClass: MockClass? = null
  private val classes = mutableListOf<Class>()
  private val protocols = mutableListOf<Protocol>()

  fun set(c: MockClass) {
    mockClass = c
    var superclass = c.superclass
    while (superclass != null) {
      classes.add(superclass)
      superclass = superclass.superclass
    }
    add(c.protocols)
  }

  private fun add(protocols: List<Protocol>) {
    protocols.forEach { this.protocols.add(it) }
    protocols.forEach { add(it.protocols) }
  }

  fun generate(): String {
    val presenter = MockViewPresenter(view)
    setScope(presenter)
    presenter.setClassInitialisers(getClassInitializersRemovingDuplicates())
    presenter.addClassProperties(getClassPropertiesRemovingDuplicates())
    presenter.addClassMethods(getClassMethodsRemovingDuplicates())
    presenter.addInitialisers(getInitializersRemovingDuplicates())
    presenter.addProperties(getPropertiesRemovingDuplicates())
    presenter.addMethods(getMethodsRemovingDuplicates())
    return presenter.generate()
  }

  private fun setScope(presenter: MockViewPresenter) {
    val mockClass = this.mockClass
    if (mockClass?.scope != null) {
      presenter.setScope(mockClass.scope)
    }
  }

  private fun getClassInitializersRemovingDuplicates(): List<Initializer> {
    return classes.flatMap { it.initializers }
  }

  private fun getClassPropertiesRemovingDuplicates(): List<Property> {
    return classes.flatMap { it.properties }
        .distinctBy { SignatureGenerator.signature(it) }
  }

  private fun getClassMethodsRemovingDuplicates(): List<Method> {
    return classes.flatMap { it.methods }
        .distinctBy { SignatureGenerator.signature(it) }
  }

  private fun getInitializersRemovingDuplicates(): List<Initializer> {
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
