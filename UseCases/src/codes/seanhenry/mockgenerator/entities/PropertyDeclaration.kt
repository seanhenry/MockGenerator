package codes.seanhenry.mockgenerator.entities

open class PropertyDeclaration(val name: String, val type: String) {
  companion object {
    val EMPTY = PropertyDeclaration("", "")
  }
}
