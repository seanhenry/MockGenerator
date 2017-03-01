protocol OverloadProtocol {
    func setValue(_ string: String, forKey key: String)
    func setValue(_ int: Int, forKey key: String)
    func set(value: String)
    func set(value: Int)
    func animate() -> Bool
    func animate(withDuration duration: TimeInterval)
    func animate(withDuration duration: TimeInterval, delay: TimeInterval)
    func present(_ viewControllerToPresent: UIViewController)
    func present(_ viewControllerToPresent: UIViewController, animated: Bool)
    func present(from viewControllerToPresent: UIViewController)
    func present(from viewControllerToPresent: UINavigationController)
}

class Mock: OverloadProtocol {

    var invokedSetValueStringForKey = false
    var invokedSetValueStringForKeyParameters: (string: String, key: String)?
    func setValue(_ string: String, forKey key: String) {
        invokedSetValueStringForKey = true
        invokedSetValueStringForKeyParameters = (string, key)
    }
    var invokedSetValueIntForKey = false
    var invokedSetValueIntForKeyParameters: (int: Int, key: String)?
    func setValue(_ int: Int, forKey key: String) {
        invokedSetValueIntForKey = true
        invokedSetValueIntForKeyParameters = (int, key)
    }
    var invokedSetValueString = false
    var invokedSetValueStringParameters: (value: String, Void)?
    func set(value: String) {
        invokedSetValueString = true
        invokedSetValueStringParameters = (value, ())
    }
    var invokedSetValueInt = false
    var invokedSetValueIntParameters: (value: Int, Void)?
    func set(value: Int) {
        invokedSetValueInt = true
        invokedSetValueIntParameters = (value, ())
    }
    var invokedAnimate = false
    var stubbedAnimateResult: Bool!
    func animate() -> Bool {
        invokedAnimate = true
        return stubbedAnimateResult
    }
    var invokedAnimateWithDuration = false
    var invokedAnimateWithDurationParameters: (duration: TimeInterval, Void)?
    func animate(withDuration duration: TimeInterval) {
        invokedAnimateWithDuration = true
        invokedAnimateWithDurationParameters = (duration, ())
    }
    var invokedAnimateWithDurationDelay = false
    var invokedAnimateWithDurationDelayParameters: (duration: TimeInterval, delay: TimeInterval)?
    func animate(withDuration duration: TimeInterval, delay: TimeInterval) {
        invokedAnimateWithDurationDelay = true
        invokedAnimateWithDurationDelayParameters = (duration, delay)
    }
    var invokedPresent = false
    var invokedPresentParameters: (viewControllerToPresent: UIViewController, Void)?
    func present(_ viewControllerToPresent: UIViewController) {
        invokedPresent = true
        invokedPresentParameters = (viewControllerToPresent, ())
    }
    var invokedPresentAnimated = false
    var invokedPresentAnimatedParameters: (viewControllerToPresent: UIViewController, animated: Bool)?
    func present(_ viewControllerToPresent: UIViewController, animated: Bool) {
        invokedPresentAnimated = true
        invokedPresentAnimatedParameters = (viewControllerToPresent, animated)
    }
    var invokedPresentFromUIViewController = false
    var invokedPresentFromUIViewControllerParameters: (viewControllerToPresent: UIViewController, Void)?
    func present(from viewControllerToPresent: UIViewController) {
        invokedPresentFromUIViewController = true
        invokedPresentFromUIViewControllerParameters = (viewControllerToPresent, ())
    }
    var invokedPresentFromUINavigationController = false
    var invokedPresentFromUINavigationControllerParameters: (viewControllerToPresent: UINavigationController, Void)?
    func present(from viewControllerToPresent: UINavigationController) {
        invokedPresentFromUINavigationController = true
        invokedPresentFromUINavigationControllerParameters = (viewControllerToPresent, ())
    }
}
