class AnotherDeclarationInTheFileShouldNotBeAffected {

    func shouldNotInterfere() {

    }
}

class SimpleProtocolMock: SimpleProtocol {
<caret>
}
