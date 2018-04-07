package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.ClosureHelper.Companion.createClosure
import codes.seanhenry.mockgenerator.ast.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class MethodParameterTest : MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method("oneParam", null, "param0: Int", "func oneParam(param0: Int)"),
        Method("twoParam", null, "param0: Int, param1: String", "func twoParam(param0: Int, param1: String)"),
        Method("optionalParam", null, "param0: Int?", "func optionalParam(param0: Int?)"),
        Method("iuoParam", null, "param0: Int!", "func iuoParam(param0: Int!)"),
        Method("noLabelParam", null, "_ name0: Int!", "func noLabelParam(_ name0: Int!)"),
        Method("nameAndLabelParam", null, "label0 name0: Int!", "func nameAndLabelParam(label0 name0: Int!)"),
        Method("closureParam", null, createClosure("", "()"), "func closureParam(closure: () -> ())")
    )
  }

  override fun getExpected(): String {
    return """
    var invokedOneParam = false
    var invokedOneParamCount = 0
    var invokedOneParamParameters: (param0: Int, Void)?
    var invokedOneParamParametersList = [(param0: Int, Void)]()
    func oneParam(param0: Int) {
    invokedOneParam = true
    invokedOneParamCount += 1
    invokedOneParamParameters = (param0, ())
    invokedOneParamParametersList.append((param0, ()))
    }
    var invokedTwoParam = false
    var invokedTwoParamCount = 0
    var invokedTwoParamParameters: (param0: Int, param1: String)?
    var invokedTwoParamParametersList = [(param0: Int, param1: String)]()
    func twoParam(param0: Int, param1: String) {
    invokedTwoParam = true
    invokedTwoParamCount += 1
    invokedTwoParamParameters = (param0, param1)
    invokedTwoParamParametersList.append((param0, param1))
    }
    var invokedOptionalParam = false
    var invokedOptionalParamCount = 0
    var invokedOptionalParamParameters: (param0: Int?, Void)?
    var invokedOptionalParamParametersList = [(param0: Int?, Void)]()
    func optionalParam(param0: Int?) {
    invokedOptionalParam = true
    invokedOptionalParamCount += 1
    invokedOptionalParamParameters = (param0, ())
    invokedOptionalParamParametersList.append((param0, ()))
    }
    var invokedIuoParam = false
    var invokedIuoParamCount = 0
    var invokedIuoParamParameters: (param0: Int?, Void)?
    var invokedIuoParamParametersList = [(param0: Int?, Void)]()
    func iuoParam(param0: Int!) {
    invokedIuoParam = true
    invokedIuoParamCount += 1
    invokedIuoParamParameters = (param0, ())
    invokedIuoParamParametersList.append((param0, ()))
    }
    var invokedNoLabelParam = false
    var invokedNoLabelParamCount = 0
    var invokedNoLabelParamParameters: (name0: Int?, Void)?
    var invokedNoLabelParamParametersList = [(name0: Int?, Void)]()
    func noLabelParam(_ name0: Int!) {
    invokedNoLabelParam = true
    invokedNoLabelParamCount += 1
    invokedNoLabelParamParameters = (name0, ())
    invokedNoLabelParamParametersList.append((name0, ()))
    }
    var invokedNameAndLabelParam = false
    var invokedNameAndLabelParamCount = 0
    var invokedNameAndLabelParamParameters: (name0: Int?, Void)?
    var invokedNameAndLabelParamParametersList = [(name0: Int?, Void)]()
    func nameAndLabelParam(label0 name0: Int!) {
    invokedNameAndLabelParam = true
    invokedNameAndLabelParamCount += 1
    invokedNameAndLabelParamParameters = (name0, ())
    invokedNameAndLabelParamParametersList.append((name0, ()))
    }
    var invokedClosureParam = false
    var invokedClosureParamCount = 0
    func closureParam(closure: () -> ()) {
    invokedClosureParam = true
    invokedClosureParamCount += 1
    closure()
    }
      """.trimIndent()
  }
}
