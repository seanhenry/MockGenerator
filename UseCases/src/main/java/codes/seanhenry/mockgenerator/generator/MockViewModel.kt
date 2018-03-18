package codes.seanhenry.mockgenerator.generator

class MockViewModel(var property: List<PropertyViewModel>,
                    var method: List<MethodViewModel>,
                    var scope: String)

class PropertyViewModel(var capitalizedUniqueName: String,
                        var hasSetter: Boolean,
                        var type: String,
                        var optionalType: String,
                        var iuoType: String,
                        var defaultValueAssignment: String,
                        var declarationText: String
)

class MethodViewModel(var capitalizedUniqueName: String,
                      var escapingParameters: ParametersViewModel?,
                      var closureParameter: List<ClosureParameterViewModel>,
                      var resultType: ResultTypeViewModel?,
                      var declarationText: String)

class ResultTypeViewModel(var defaultValueAssignment: String,
                          var optionalType: String,
                          var iuoType: String
//                          var returnCastStatement: String
)

class ParametersViewModel(var tupleRepresentation: String, var tupleAssignment: String)

class ClosureParameterViewModel(var capitalizedName: String,
                                var name: String,
                                var argumentsTupleRepresentation: String,
                                var implicitClosureCall: String,
                                var hasArguments: Boolean)
