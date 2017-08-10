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

    var invokedKeywords1 = false
    var invokedKeywords1Count = 0
    var invokedKeywords1Parameters: (while: Int, repeat: Int, switch: Int, case : Int, default: Int, do: Int, catch: Int, defer: Int)?
    var invokedKeywords1ParametersList = [(while: Int, repeat: Int, switch: Int, case: Int, default: Int, do: Int, catch: Int, defer: Int)]()

    func keywords1(while: Int, repeat: Int, switch: Int, case: Int, default: Int, do: Int, catch: Int, defer: Int) {
        invokedKeywords1 = true
        invokedKeywords1Count += 1
        invokedKeywords1Parameters = (`while`, `repeat`, `switch`, `case`, `default`, `do`, `catch`, `defer`)
        invokedKeywords1ParametersList.append((`while`, `repeat`, `switch`, `case`, `default`, `do`, `catch`, `defer`))
    }
}
