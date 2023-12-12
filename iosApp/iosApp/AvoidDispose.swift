import UIKit
import SharedSDK

class AvoidDispose: UIViewController {

    let controller: UIViewController


    init(_ controller: UIViewController) {
        self.controller = controller

        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        addChild(controller)
        view.addSubview(controller.view)
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
    }

    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        controller.removeFromParent()
        controller.view.removeFromSuperview()
    }

    override func viewWillTransition(to size: CGSize, with coordinator: UIViewControllerTransitionCoordinator) {
        super.viewWillTransition(to: size, with: coordinator)
        skiaRefresh()
    }

    override func traitCollectionDidChange(_ previousTraitCollection: UITraitCollection?) {
        super.traitCollectionDidChange(previousTraitCollection)
        skiaRefresh()
    }

    override func viewSafeAreaInsetsDidChange() {
        super.viewSafeAreaInsetsDidChange()
        skiaRefresh()
    }

    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }

    private func skiaRefresh() {
        controller.view.frame = view.bounds
        controller.viewWillAppear(false)
    
        //kotlin compose refresh
        controller.view.touchesCancelled([UITouch()], with: UIEvent())
    }

}
