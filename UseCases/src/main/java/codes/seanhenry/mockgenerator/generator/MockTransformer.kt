package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import codes.seanhenry.mockgenerator.entities.ProtocolProperty

interface MockTransformer {

  fun setScope(scope: String)
  fun add(method: ProtocolMethod)
  fun add(property: ProtocolProperty)
  fun add(vararg initialisers: Initialiser)
  fun add(vararg methods: ProtocolMethod)
  fun add(vararg properties: ProtocolProperty)
  fun addInitialisers(initialisers: List<Initialiser>)
  fun addMethods(methods: List<ProtocolMethod>)
  fun addProperties(properties: List<ProtocolProperty>)
  fun setClassInitialisers(vararg initialisers: Initialiser)
  fun setClassInitialisers(initialisers: List<Initialiser>)
  fun addClassMethods(vararg methods: ProtocolMethod)
  fun addClassMethods(methods: List<ProtocolMethod>)
  fun addClassProperties(vararg properties: ProtocolProperty)
  fun addClassProperties(properties: List<ProtocolProperty>)
  fun generate(): String
}
