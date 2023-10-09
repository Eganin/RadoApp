package settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import settings.models.SettingsAllUserInfo
import settings.models.SettingsLoginUserInfo

class SettingsAuthDataSource(
    private val settings: Settings
) {

    fun saveUserId(userId: Int) {
        settings.putInt(key = USER_ID_KEY, value = userId)
    }

    private fun fetchUserId(): Int {
        return settings.get(key = USER_ID_KEY, defaultValue = ID_DEFAULT_VALUE)
    }

    fun saveLoginUserInfo(position: String, fullName: String, phone: String) {
        settings.apply {
            putString(key = POSITION_KEY, value = position)
            putString(key = FULL_NAME_KEY, value = fullName)
            putString(key = PHONE_NAME_KEY, value = phone)
        }
    }

    fun fetchLoginUserInfo(): SettingsLoginUserInfo {
        return SettingsLoginUserInfo(
            position = settings.getString(key = POSITION_KEY, defaultValue = STRING_DEFAULT_VALUE),
            fullName = settings.getString(key = FULL_NAME_KEY, defaultValue = STRING_DEFAULT_VALUE),
            phone = settings.getString(key = PHONE_NAME_KEY, defaultValue = STRING_DEFAULT_VALUE)
        )
    }

    fun fetchAllUserInfo(): SettingsAllUserInfo {
        return SettingsAllUserInfo(
            userId = fetchUserId(),
            position = settings.getString(key = POSITION_KEY, defaultValue = STRING_DEFAULT_VALUE),
            fullName = settings.getString(key = FULL_NAME_KEY, defaultValue = STRING_DEFAULT_VALUE),
            phone = settings.getString(key = PHONE_NAME_KEY, defaultValue = STRING_DEFAULT_VALUE)
        )
    }

    private companion object {
        const val USER_ID_KEY = "USER_ID_KEY_ZA_WARUDO"
        const val POSITION_KEY = "POSITION_KEY_ZA_WARUDO"
        const val FULL_NAME_KEY = "FULL_NAME_KEY_ZA_WARUDO"
        const val PHONE_NAME_KEY = "PHONE_NAME_KEY_ZA_WARUDO"
        const val STRING_DEFAULT_VALUE = ""
        const val ID_DEFAULT_VALUE = -1
    }
}