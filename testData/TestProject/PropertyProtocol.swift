protocol PropertyProtocol {
    var readWrite: String { set get }
    var readOnly: Int { get }
    var optional: UInt? { get set }
    var unwrapped: String! { get set }
    weak var weakVar: AnyObject? { get set }
    func method()
}
