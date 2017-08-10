@testable import MockableTypes

class KeywordsMock: Keywords {

    var invokedRun = false
    var invokedRunCount = 0
    var invokedRunParameters: (for: Int, Void)?
    var invokedRunParametersList = [(for: Int, Void)]()

    func run(for: Int) {
        invokedRun = true
        invokedRunCount += 1
        invokedRunParameters = (`for`, ())
        invokedRunParametersList.append((`for`, ()))
    }
}
