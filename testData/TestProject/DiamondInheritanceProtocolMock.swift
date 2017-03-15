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
<caret>
}
