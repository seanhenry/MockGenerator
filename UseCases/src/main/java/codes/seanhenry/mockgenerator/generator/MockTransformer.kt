package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Property

interface MockTransformer {

  fun setScope(scope: String)
  fun add(method: Method)
  fun add(property: Property)
  fun add(vararg initialisers: Initialiser)
  fun add(vararg methods: Method)
  fun add(vararg properties: Property)
  fun addInitialisers(initialisers: List<Initialiser>)
  fun addMethods(methods: List<Method>)
  fun addProperties(properties: List<Property>)
  fun setClassInitialisers(vararg initialisers: Initialiser)
  fun setClassInitialisers(initialisers: List<Initialiser>)
  fun addClassMethods(vararg methods: Method)
  fun addClassMethods(methods: List<Method>)
  fun addClassProperties(vararg properties: Property)
  fun addClassProperties(properties: List<Property>)
  fun generate(): String
}
