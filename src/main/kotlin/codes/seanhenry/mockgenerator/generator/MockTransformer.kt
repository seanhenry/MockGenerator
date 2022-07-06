package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.entities.Initializer
import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.entities.Subscript

interface MockTransformer {

  fun setScope(scope: String)
  fun add(method: Method)
  fun add(property: Property)
  fun add(subscript: Subscript)
  fun add(vararg initializers: Initializer)
  fun add(vararg methods: Method)
  fun add(vararg properties: Property)
  fun add(vararg subscripts: Subscript)
  fun addInitialisers(initializers: List<Initializer>)
  fun addMethods(methods: List<Method>)
  fun addProperties(properties: List<Property>)
  fun addSubscripts(subscripts: List<Subscript>)
  fun setClassInitialisers(vararg initializers: Initializer)
  fun setClassInitialisers(initializers: List<Initializer>)
  fun addClassMethods(vararg methods: Method)
  fun addClassMethods(methods: List<Method>)
  fun addClassProperties(vararg properties: Property)
  fun addClassProperties(properties: List<Property>)
  fun addClassSubscripts(vararg subscripts: Subscript)
  fun addClassSubscripts(subscripts: List<Subscript>)
  fun generate(): String
}
