import UIKit

class MockUIKitProtocol: NSObject, UITextFieldDelegate {

    var invokedTextFieldShouldBeginEditing = false
    var invokedTextFieldShouldBeginEditingCount = 0
    var invokedTextFieldShouldBeginEditingParameters: (textField: UITextField, Void)?
    var invokedTextFieldShouldBeginEditingParametersList = [(textField: UITextField, Void)]()
    var stubbedTextFieldShouldBeginEditingResult: Bool!
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        invokedTextFieldShouldBeginEditing = true
        invokedTextFieldShouldBeginEditingCount += 1
        invokedTextFieldShouldBeginEditingParameters = (textField, ())
        return stubbedTextFieldShouldBeginEditingResult
    }
    var invokedTextFieldDidBeginEditing = false
    var invokedTextFieldDidBeginEditingCount = 0
    var invokedTextFieldDidBeginEditingParameters: (textField: UITextField, Void)?
    var invokedTextFieldDidBeginEditingParametersList = [(textField: UITextField, Void)]()
    func textFieldDidBeginEditing(_ textField: UITextField) {
        invokedTextFieldDidBeginEditing = true
        invokedTextFieldDidBeginEditingCount += 1
        invokedTextFieldDidBeginEditingParameters = (textField, ())
    }
    var invokedTextFieldShouldEndEditing = false
    var invokedTextFieldShouldEndEditingCount = 0
    var invokedTextFieldShouldEndEditingParameters: (textField: UITextField, Void)?
    var invokedTextFieldShouldEndEditingParametersList = [(textField: UITextField, Void)]()
    var stubbedTextFieldShouldEndEditingResult: Bool!
    func textFieldShouldEndEditing(_ textField: UITextField) -> Bool {
        invokedTextFieldShouldEndEditing = true
        invokedTextFieldShouldEndEditingCount += 1
        invokedTextFieldShouldEndEditingParameters = (textField, ())
        return stubbedTextFieldShouldEndEditingResult
    }
    var invokedTextFieldDidEndEditing = false
    var invokedTextFieldDidEndEditingCount = 0
    var invokedTextFieldDidEndEditingParameters: (textField: UITextField, Void)?
    var invokedTextFieldDidEndEditingParametersList = [(textField: UITextField, Void)]()
    func textFieldDidEndEditing(_ textField: UITextField) {
        invokedTextFieldDidEndEditing = true
        invokedTextFieldDidEndEditingCount += 1
        invokedTextFieldDidEndEditingParameters = (textField, ())
    }
    var invokedTextFieldDidEndEditingReason = false
    var invokedTextFieldDidEndEditingReasonCount = 0
    var invokedTextFieldDidEndEditingReasonParameters: (textField: UITextField, reason: UITextFieldDidEndEditingReason)?
    var invokedTextFieldDidEndEditingReasonParametersList = [(textField: UITextField, reason: UITextFieldDidEndEditingReason)]()
    func textFieldDidEndEditing(_ textField: UITextField, reason: UITextFieldDidEndEditingReason) {
        invokedTextFieldDidEndEditingReason = true
        invokedTextFieldDidEndEditingReasonCount += 1
        invokedTextFieldDidEndEditingReasonParameters = (textField, reason)
    }
    var invokedTextField = false
    var invokedTextFieldCount = 0
    var invokedTextFieldParameters: (textField: UITextField, range: NSRange, string: String)?
    var invokedTextFieldParametersList = [(textField: UITextField, range: NSRange, string: String)]()
    var stubbedTextFieldResult: Bool!
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        invokedTextField = true
        invokedTextFieldCount += 1
        invokedTextFieldParameters = (textField, range, string)
        return stubbedTextFieldResult
    }
    var invokedTextFieldShouldClear = false
    var invokedTextFieldShouldClearCount = 0
    var invokedTextFieldShouldClearParameters: (textField: UITextField, Void)?
    var invokedTextFieldShouldClearParametersList = [(textField: UITextField, Void)]()
    var stubbedTextFieldShouldClearResult: Bool!
    func textFieldShouldClear(_ textField: UITextField) -> Bool {
        invokedTextFieldShouldClear = true
        invokedTextFieldShouldClearCount += 1
        invokedTextFieldShouldClearParameters = (textField, ())
        return stubbedTextFieldShouldClearResult
    }
    var invokedTextFieldShouldReturn = false
    var invokedTextFieldShouldReturnCount = 0
    var invokedTextFieldShouldReturnParameters: (textField: UITextField, Void)?
    var invokedTextFieldShouldReturnParametersList = [(textField: UITextField, Void)]()
    var stubbedTextFieldShouldReturnResult: Bool!
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        invokedTextFieldShouldReturn = true
        invokedTextFieldShouldReturnCount += 1
        invokedTextFieldShouldReturnParameters = (textField, ())
        return stubbedTextFieldShouldReturnResult
    }
}
