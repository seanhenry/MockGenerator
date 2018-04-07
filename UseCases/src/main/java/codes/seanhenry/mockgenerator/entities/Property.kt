package codes.seanhenry.mockgenerator.entities

class Property(val name: String, val type: String, val isWritable: Boolean, val declarationText: String) {

  fun getTrimmedSignature(): String {
    return declarationText.split("{")[0].trimEnd(' ', '\t', '\n')
  }
}
