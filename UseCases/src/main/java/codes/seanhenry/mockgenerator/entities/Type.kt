package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.visitor.Visitor

interface Type {
  val text: String
  fun accept(visitor: Visitor)
}
