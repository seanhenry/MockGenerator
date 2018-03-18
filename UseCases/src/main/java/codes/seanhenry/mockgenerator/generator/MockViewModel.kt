package codes.seanhenry.mockgenerator.generator

class MockViewModel(var property: List<PropertyViewModel>, var method: List<MethodViewModel>)

class PropertyViewModel(var capitalizedUniqueName: String,
                        var hasSetter: Boolean,
                        var type: String,
                        var nonOptionalType: String,
                        var nonOptionalTypeRecursively: String,
                        var defaultValueAssignment: String,
                        var declarationText: String
)

class MethodViewModel(var capitalizedUniqueName: String,
                      var escapingParameters: ParametersViewModel?,
//                      var closureParameter: [ClosureParameterViewModel],
                      var resultType: ResultTypeViewModel?,
                      var declarationText: String)

class ResultTypeViewModel(var defaultValueAssignment: String,
                          var nonOptionalType: String,
                          var nonOptionalTypeRecursively: String
//                          var returnCastStatement: String
)

class ParametersViewModel(var tupleRepresentation: String, var tupleAssignment: String)
