@testable import MockableTypes

class ArgumentInitialiserProtocolMock: ArgumentInitialiserProtocol {

    required init(a: Int, b: String, _ c: String?) {
    }
}
