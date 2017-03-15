protocol DiamondA {
    func a()
}

protocol DiamondB: DiamondA {
    func b()
}

protocol DiamondC: A {
    func c()
}

class Mock: DiamondC, DiamondB {

    var invokedC = false
    func c() {
        invokedC = true
    }
    var invokedB = false
    func b() {
        invokedB = true
    }
    var invokedA = false
    func a() {
        invokedA = true
    }
}
