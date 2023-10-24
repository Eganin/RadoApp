package other

sealed class WrapperForResponse{
    data class Success(val message:String=""): WrapperForResponse()

    data class Failure(val message:String=""): WrapperForResponse()
}