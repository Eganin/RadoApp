class RequestCancelledException(
    val permission: Permission,
    message:String? =null
): Exception(message)