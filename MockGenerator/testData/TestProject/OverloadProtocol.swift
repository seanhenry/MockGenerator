import Foundation
import UIKit

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
