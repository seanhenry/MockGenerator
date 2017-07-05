protocol OptionalProtocol {
    func optionals(optional: Double?) -> Int?
    func unwrappedOptionals(unwrapped: UInt!) -> UInt!
    func mixed(unwrapped: UInt!, optional: String?, value: Int) -> String
    func arrayOptionals(optional: [UInt]?, unwrapped: [String]!, value: [Int], optionalValue: [String?]?) -> [String]?
}
