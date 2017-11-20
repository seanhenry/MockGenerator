open class OpenClass {
    init(a: String?) {}
    private var privateProperty: String? { return nil }
    fileprivate var fileprivateProperty: String? { return nil }
    internal var internalProperty: String? { return nil }
    public var publicProperty: String? { return nil }
    open var openProperty: String? { return nil }
    private func privateMethod() {}
    fileprivate func fileprivateMethod() {}
    internal func internalMethod() {}
    public func publicMethod() {}
    open func openMethod() {}
}
