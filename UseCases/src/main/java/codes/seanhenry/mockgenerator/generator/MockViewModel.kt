package codes.seanhenry.mockgenerator.generator

class MockViewModel(var method: List<MethodViewModel>)

class MethodViewModel(var capitalizedUniqueName: String,
                      var escapingParameters: ParametersViewModel?,
//                      var closureParameter: [ClosureParameterViewModel],
//                      var resultType: ResultTypeViewModel,
                      var declarationText: String)

class ParametersViewModel(var tupleRepresentation: String, var tupleAssignment: String)
