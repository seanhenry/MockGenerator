package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.OptionalUtil

class SwiftStringCastReturnProperty {

  fun transform(property: PropertyDeclaration, originalType: String): String {
    val returnProperty = SwiftStringReturnProperty().transform(property)
    if (OptionalUtil.isOptional(originalType)) {
      return "$returnProperty as? ${OptionalUtil.removeOptional(originalType)}"
    }
    return "$returnProperty as! $originalType"
  }
}
