import other.WrapperForResponse

interface RequestsForMechanicRepository {

    suspend fun confirmationRequest(requestId: Int, date: String, time: String): WrapperForResponse

    suspend fun rejectRequest(requestId: Int, commentMechanic: String): WrapperForResponse
}