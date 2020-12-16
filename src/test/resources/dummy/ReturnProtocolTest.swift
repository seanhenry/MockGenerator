func returnType() -> String {
return ""
}
func returnTuple() -> (String, Int?) {
return ("", nil)
}
func returnLabelledTuple() -> (s: String, i: Int?) {
return ("", nil)
}
func returnOptional() -> Int? {
return nil
}
func returnIUO() -> UInt! {
return nil
}
func returnGeneric() -> Optional<String> {
return nil
}
func returnOptionalGeneric() -> Optional<String>? {
return nil
}
func returnClosure() -> () -> () {
return { }
}
func returnComplicatedClosure() -> ((String, Int?) -> (UInt)) {
return { _, _ in return 0 }
}
func returnOptionalClosure() -> (() -> ())? {
return nil
}
func returnExplicitVoid() -> Void {
return ()
}
func returnClosure() -> (() -> ()) {
return { }
}
func returnClosureArgs() -> (Int, String) -> (String) {
return { _, _ in return "" }
}
func closureA() -> () -> () {
return { }
}
func closureB() -> () -> (Void) {
return { }
}
func closureC() -> () -> Void {
return { }
}
func closureD() -> (String, Int) -> () {
return { _, _ in }
}
func closureE() -> () -> (String) {
return { return "" }
}