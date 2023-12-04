open class DeniedException(
    val permission: Permission,
    message: String? = null
): Exception(message)

class DeniedAlwaysException(
    permission: Permission,
    message: String?=null
): DeniedException(permission=permission,message=message)