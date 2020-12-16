var stubbedInt: Int! = 0
var int: Int {
set {
}
get {
return stubbedInt
}
}
var stubbedIntAddingResult: Int! = 0
func int(adding: Int) -> Int {
return stubbedIntAddingResult
}
func setValue(_ string: String, forKey key: String) {
}
func setValue(_ int: Int, forKey key: String) {
}
func set(value: String) {
}
func set(value: Int) {
}
var stubbedAnimateResult: Bool! = false
func animate() -> Bool {
return stubbedAnimateResult
}
func animate(withDuration duration: TimeInterval) {
}
func animate(withDuration duration: TimeInterval, delay: TimeInterval) {
}
func specialCharacters(_ tuple: (String, Int)) {
}
func specialCharacters(_ tuple: (UInt, Float)) {
}