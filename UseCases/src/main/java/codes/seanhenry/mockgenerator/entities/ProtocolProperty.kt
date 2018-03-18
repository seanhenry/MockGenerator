package codes.seanhenry.mockgenerator.entities

// TODO: change signature to declarationText
// TODO: Change ProtocolProperty to Property (and Method)
class ProtocolProperty(val name: String, val type: String, val isWritable: Boolean, val signature: String) {

  fun getTrimmedSignature(): String {
    return signature.split("{")[0].trimEnd(' ', '\t', '\n')
  }
}
