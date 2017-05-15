class MockOptionalProtocol: OptionalProtocol {

    var invokedOptionals = false
    var invokedOptionalsCount = 0
    var invokedOptionalsParameters: (optional: Double?, Void)?
    var invokedOptionalsParametersList = [(optional: Double?, Void)]()
    var stubbedOptionalsResult: Int!
    func optionals(optional: Double?) -> Int? {
        invokedOptionals = true
        invokedOptionalsCount += 1
        invokedOptionalsParameters = (optional, ())
        return stubbedOptionalsResult
    }
    var invokedUnwrappedOptionals = false
    var invokedUnwrappedOptionalsCount = 0
    var invokedUnwrappedOptionalsParameters: (unwrapped: UInt?, Void)?
    var invokedUnwrappedOptionalsParametersList = [(unwrapped: UInt?, Void)]()
    var stubbedUnwrappedOptionalsResult: UInt!
    func unwrappedOptionals(unwrapped: UInt!) -> UInt! {
        invokedUnwrappedOptionals = true
        invokedUnwrappedOptionalsCount += 1
        invokedUnwrappedOptionalsParameters = (unwrapped, ())
        return stubbedUnwrappedOptionalsResult
    }
    var invokedMixed = false
    var invokedMixedCount = 0
    var invokedMixedParameters: (unwrapped: UInt?, optional: String?, value: Int)?
    var invokedMixedParametersList = [(unwrapped: UInt?, optional: String?, value: Int)]()
    var stubbedMixedResult: String!
    func mixed(unwrapped: UInt!, optional: String?, value: Int) -> String {
        invokedMixed = true
        invokedMixedCount += 1
        invokedMixedParameters = (unwrapped, optional, value)
        return stubbedMixedResult
    }
}
