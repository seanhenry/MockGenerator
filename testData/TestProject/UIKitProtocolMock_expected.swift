import UIKit

class MockUIKitProtocol: NSObject, UITextFieldDelegate {

    var invokedTextFieldShouldBeginEditing = false
    var invokedTextFieldShouldBeginEditingParameters: (textField: UITextField, Void)?
    var stubbedTextFieldShouldBeginEditingResult: Bool!
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        invokedTextFieldShouldBeginEditing = true
        invokedTextFieldShouldBeginEditingParameters = (textField, ())
        return stubbedTextFieldShouldBeginEditingResult
    }
    var invokedTextFieldDidBeginEditing = false
    var invokedTextFieldDidBeginEditingParameters: (textField: UITextField, Void)?
    func textFieldDidBeginEditing(_ textField: UITextField) {
        invokedTextFieldDidBeginEditing = true
        invokedTextFieldDidBeginEditingParameters = (textField, ())
    }
    var invokedTextFieldShouldEndEditing = false
    var invokedTextFieldShouldEndEditingParameters: (textField: UITextField, Void)?
    var stubbedTextFieldShouldEndEditingResult: Bool!
    func textFieldShouldEndEditing(_ textField: UITextField) -> Bool {
        invokedTextFieldShouldEndEditing = true
        invokedTextFieldShouldEndEditingParameters = (textField, ())
        return stubbedTextFieldShouldEndEditingResult
    }
    var invokedTextFieldDidEndEditing = false
    var invokedTextFieldDidEndEditingParameters: (textField: UITextField, Void)?
    func textFieldDidEndEditing(_ textField: UITextField) {
        invokedTextFieldDidEndEditing = true
        invokedTextFieldDidEndEditingParameters = (textField, ())
    }
    var invokedTextFieldDidEndEditingReason = false
    var invokedTextFieldDidEndEditingReasonParameters: (textField: UITextField, reason: UITextFieldDidEndEditingReason)?
    func textFieldDidEndEditing(_ textField: UITextField, reason: UITextFieldDidEndEditingReason) {
        invokedTextFieldDidEndEditingReason = true
        invokedTextFieldDidEndEditingReasonParameters = (textField, reason)
    }
    var invokedTextField = false
    var invokedTextFieldParameters: (textField: UITextField, range: NSRange, string: String)?
    var stubbedTextFieldResult: Bool!
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        invokedTextField = true
        invokedTextFieldParameters = (textField, range, string)
        return stubbedTextFieldResult
    }
    var invokedTextFieldShouldClear = false
    var invokedTextFieldShouldClearParameters: (textField: UITextField, Void)?
    var stubbedTextFieldShouldClearResult: Bool!
    func textFieldShouldClear(_ textField: UITextField) -> Bool {
        invokedTextFieldShouldClear = true
        invokedTextFieldShouldClearParameters = (textField, ())
        return stubbedTextFieldShouldClearResult
    }
    var invokedTextFieldShouldReturn = false
    var invokedTextFieldShouldReturnParameters: (textField: UITextField, Void)?
    var stubbedTextFieldShouldReturnResult: Bool!
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        invokedTextFieldShouldReturn = true
        invokedTextFieldShouldReturnParameters = (textField, ())
        return stubbedTextFieldShouldReturnResult
    }
}
