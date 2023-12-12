import android.content.Context
import android.content.Intent
import android.net.Uri

class PhoneControllerImpl(
    private val applicationContext:Context
): PhoneController {
    override fun openDialerPhone(phoneNumber:String) {
        val url = Uri.parse("tel:$phoneNumber")
        val intent = Intent(Intent.ACTION_DIAL, url)
        applicationContext.startActivity(intent)
    }
}