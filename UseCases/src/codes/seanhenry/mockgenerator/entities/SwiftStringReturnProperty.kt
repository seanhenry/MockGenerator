package codes.seanhenry.mockgenerator.entities

class SwiftStringReturnProperty {

  fun transform(property: PropertyDeclaration): String {
    return "return " + property.name
  }
}
