open class DeniedExpeption(
    val permission: Permission,
    message: String? = null
): Exception(message)

class DeniedAlwaysException(
    permission: Permission,
    message: String?=null
): DeniedExpeption(permission=permission,message=message)