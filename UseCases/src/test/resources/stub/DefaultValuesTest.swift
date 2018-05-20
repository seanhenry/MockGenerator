var stubbedInt: Int! = 0
var int: Int {
set {
}
get {
return stubbedInt
}
}
var stubbedOpt: Int!
var opt: Optional<Int> {
set {
}
get {
return stubbedOpt
}
}
var stubbedShortOptional: Int!
var shortOptional: Int? {
set {
}
get {
return stubbedShortOptional
}
}
var stubbedDoubleOptional: Int!
var doubleOptional: Int?? {
set {
}
get {
return stubbedDoubleOptional
}
}
var stubbedIuo: Int!
var iuo: Int! {
set {
}
get {
return stubbedIuo
}
}
var stubbedOptionalIntResult: Int!
func optionalInt() -> Int? {
return stubbedOptionalIntResult
}
var stubbedIuoIntResult: Int!
func iuoInt() -> Int! {
return stubbedIuoIntResult
}
var stubbedDoubleResult: Double! = 0
func double() -> Double {
return stubbedDoubleResult
}
var stubbedFloatResult: Float! = 0
func float() -> Float {
return stubbedFloatResult
}
var stubbedInt16Result: Int16! = 0
func int16() -> Int16 {
return stubbedInt16Result
}
var stubbedInt32Result: Int32! = 0
func int32() -> Int32 {
return stubbedInt32Result
}
var stubbedInt64Result: Int64! = 0
func int64() -> Int64 {
return stubbedInt64Result
}
var stubbedInt8Result: Int8! = 0
func int8() -> Int8 {
return stubbedInt8Result
}
var stubbedUIntResult: UInt! = 0
func uInt() -> UInt {
return stubbedUIntResult
}
var stubbedUInt16Result: UInt16! = 0
func uInt16() -> UInt16 {
return stubbedUInt16Result
}
var stubbedUInt32Result: UInt32! = 0
func uInt32() -> UInt32 {
return stubbedUInt32Result
}
var stubbedUInt64Result: UInt64! = 0
func uInt64() -> UInt64 {
return stubbedUInt64Result
}
var stubbedUInt8Result: UInt8! = 0
func uInt8() -> UInt8 {
return stubbedUInt8Result
}
var stubbedArrayResult: Array<String>! = []
func array() -> Array<String> {
return stubbedArrayResult
}
var stubbedArrayLiteralResult: [String]! = []
func arrayLiteral() -> [String] {
return stubbedArrayLiteralResult
}
var stubbedArraySliceResult: ArraySlice<String>! = []
func arraySlice() -> ArraySlice<String> {
return stubbedArraySliceResult
}
var stubbedContiguousArrayResult: ContiguousArray<String>! = []
func contiguousArray() -> ContiguousArray<String> {
return stubbedContiguousArrayResult
}
var stubbedSetResult: Set<String>! = []
func set() -> Set<String> {
return stubbedSetResult
}
var stubbedOptionalArrayResult: Array<String>!
func optionalArray() -> Optional<Array<String>> {
return stubbedOptionalArrayResult
}
var stubbedShortOptionalArrayResult: [String]!
func shortOptionalArray() -> [String]? {
return stubbedShortOptionalArrayResult
}
var stubbedDictionaryResult: Dictionary<String, String>! = [:]
func dictionary() -> Dictionary<String, String> {
return stubbedDictionaryResult
}
var stubbedDictionaryLiteralResult: DictionaryLiteral<String, String>! = [:]
func dictionaryLiteral() -> DictionaryLiteral<String, String> {
return stubbedDictionaryLiteralResult
}
var stubbedDictionaryShorthandResult: [String: String]! = [:]
func dictionaryShorthand() -> [String: String] {
return stubbedDictionaryShorthandResult
}
var stubbedOptionalDictResult: Dictionary<String, String>!
func optionalDict() -> Optional<Dictionary<String, String>> {
return stubbedOptionalDictResult
}
var stubbedShortOptionalDictResult: [String: String]!
func shortOptionalDict() -> [String: String]? {
return stubbedShortOptionalDictResult
}
var stubbedBoolResult: Bool! = false
func bool() -> Bool {
return stubbedBoolResult
}
var stubbedUnicodeScalarResult: UnicodeScalar! = "!"
func unicodeScalar() -> UnicodeScalar {
return stubbedUnicodeScalarResult
}
var stubbedCharacterResult: Character! = "!"
func character() -> Character {
return stubbedCharacterResult
}
var stubbedStaticStringResult: StaticString! = ""
func staticString() -> StaticString {
return stubbedStaticStringResult
}
var stubbedStringResult: String! = ""
func string() -> String {
return stubbedStringResult
}