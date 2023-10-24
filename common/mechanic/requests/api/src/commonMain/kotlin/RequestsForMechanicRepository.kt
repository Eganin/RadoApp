import models.ConfirmationRequestRemote
import models.RejectRequestRemote
import other.WrapperForResponse

interface RequestsForMechanicRepository {

    suspend fun confirmationRequest(request:ConfirmationRequestRemote): WrapperForResponse

    suspend fun rejectRequest(request: RejectRequestRemote):WrapperForResponse
}