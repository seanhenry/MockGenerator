package codes.seanhenry.mockgenerator.entities

class ProtocolProperty(val name: String, val type: String, val isWritable: Boolean, val signature: String) {

  fun getTrimmedSignature(): String {
    return signature.split("{")[0].trimEnd(' ', '\t', '\n')
  }
}
