package codes.seanhenry.mockgenerator.entities

open class Type(open val typeName: String) {

  companion object {
    fun create(typeName: String?): Type? {
      if (typeName != null) {
        return Type(typeName)
      }
      return null
    }
  }
}
