import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class PhoneControllerImpl: PhoneController {
    override fun openDialerPhone(phoneNumber:String) {
        val phoneURL: NSURL = NSURL.URLWithString("tel://$phoneNumber")!!
        UIApplication.sharedApplication.openURL(phoneURL)
    }
}