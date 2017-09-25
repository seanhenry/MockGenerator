@testable import MockableTypes

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
        invokedOptionalsParametersList.append((optional, ()))
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
        invokedUnwrappedOptionalsParametersList.append((unwrapped, ()))
        return stubbedUnwrappedOptionalsResult
    }

    var invokedMixed = false
    var invokedMixedCount = 0
    var invokedMixedParameters: (unwrapped: UInt?, optional: String?, value: Int)?
    var invokedMixedParametersList = [(unwrapped: UInt?, optional: String?, value: Int)]()
    var stubbedMixedResult: Object!

    func mixed(unwrapped: UInt!, optional: String?, value: Int) -> Object {
        invokedMixed = true
        invokedMixedCount += 1
        invokedMixedParameters = (unwrapped, optional, value)
        invokedMixedParametersList.append((unwrapped, optional, value))
        return stubbedMixedResult
    }

    var invokedArrayOptionals = false
    var invokedArrayOptionalsCount = 0
    var invokedArrayOptionalsParameters: (optional: [UInt]?, unwrapped: [String]?, value: [Int], optionalValue: [String?]?)?
    var invokedArrayOptionalsParametersList = [(optional: [UInt]?, unwrapped: [String]?, value: [Int], optionalValue: [String?]?)]()
    var stubbedArrayOptionalsResult: [String]!

    func arrayOptionals(optional: [UInt]?, unwrapped: [String]!, value: [Int], optionalValue: [String?]?) -> [String]? {
        invokedArrayOptionals = true
        invokedArrayOptionalsCount += 1
        invokedArrayOptionalsParameters = (optional, unwrapped, value, optionalValue)
        invokedArrayOptionalsParametersList.append((optional, unwrapped, value, optionalValue))
        return stubbedArrayOptionalsResult
    }
}
