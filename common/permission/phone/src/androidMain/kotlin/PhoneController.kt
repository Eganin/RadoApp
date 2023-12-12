import android.app.Activity

actual interface PhoneController {
    actual fun openDialerPhone(phoneNumber:String)

    companion object{
        operator fun invoke(
            applicationContext:Activity
        ):PhoneController{
            return PhoneControllerImpl(
                applicationContext=applicationContext
            )
        }
    }
}