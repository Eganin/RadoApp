package other

sealed class WrapperForResponse{
    data class Success(val message:String="")

    data class Failure(val message:String="")
}