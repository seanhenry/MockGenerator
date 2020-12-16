func generic1<T>() -> T {
fatalError("Dummy implementation")
}
func generic2<T>() -> T? {
return nil
}
func generic3<T>() -> T! {
return nil
}
func genericArray<T>() -> [T] {
return []
}