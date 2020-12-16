package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.visitor.Visitor

interface Element {
  fun accept(visitor: Visitor)
}
