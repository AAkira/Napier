import UIKit
import mpp_sample

final class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        configure()
    }
}

private extension ViewController {
    func configure() {
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 24))
        label.center = CGPoint(x: 180, y: 300)
        label.textAlignment = .center
        label.font = label.font.withSize(25)
        
        let sample = Sample()
        label.text = sample.hello()
        
        view.addSubview(label)
        
        sample.suspendHelloKt()
        
        sample.handleError()
    }
}
