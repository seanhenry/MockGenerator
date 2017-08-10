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

    var invokedFor = false
    var invokedForCount = 0
    var invokedForParameters: (in : String, Void)?
    var invokedForParametersList = [(in: String, Void)]()

    func `for`(in: String) {
        invokedFor = true
        invokedForCount += 1
        invokedForParameters = (`in`, ())
        invokedForParametersList.append((`in`, ()))
    }
}
