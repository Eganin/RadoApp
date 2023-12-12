actual interface PhoneController {
    actual fun openDialerPhone(phoneNumber: String)

    companion object{
        operator fun invoke():PhoneController{
            return PhoneControllerImpl()
        }
    }

}