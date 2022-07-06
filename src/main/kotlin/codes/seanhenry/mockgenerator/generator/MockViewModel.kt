package codes.seanhenry.mockgenerator.generator

class MockViewModel(var initializer: List<InitializerViewModel>,
                    var property: List<PropertyViewModel>,
                    var method: List<MethodViewModel>,
                    var subscript: List<SubscriptViewModel>,
                    var scope: String?)

class PropertyViewModel(var name: String,
                        var capitalizedUniqueName: String,
                        var hasSetter: Boolean,
                        var type: String,
                        var optionalType: String,
                        var iuoType: String,
                        var defaultValueAssignment: String,
                        var defaultValue: String?,
                        var isImplemented: Boolean,
                        var declarationText: String)

class MethodViewModel(var capitalizedUniqueName: String,
                      var escapingParameters: ParametersViewModel?,
                      var closureParameter: List<ClosureParameterViewModel>,
                      var resultType: ResultTypeViewModel?,
                      var functionCall: String?,
                      var throws: Boolean,
                      var rethrows: Boolean,
                      var isImplemented: Boolean,
                      var declarationText: String)

class ResultTypeViewModel(var defaultValueAssignment: String,
                          var defaultValue: String?,
                          var optionalType: String,
                          var iuoType: String,
                          var type: String,
                          var returnCastStatement: String)

class ParametersViewModel(var tupleRepresentation: String,
                          var tupleAssignment: String)

class ClosureParameterViewModel(var capitalizedName: String,
                                var name: String,
                                var argumentsTupleRepresentation: String,
                                var implicitClosureCall: String,
                                var hasArguments: Boolean)

class InitializerViewModel(var declarationText: String,
                           var initializerCall: String)

class SubscriptViewModel(var capitalizedUniqueName: String,
                         var escapingParameters: ParametersViewModel?,
                         var hasSetter: Boolean,
                         var resultType: ResultTypeViewModel,
                         var functionCall: String?,
                         var isImplemented: Boolean,
                         var declarationText: String)
