package services.request

import dao.request.RequestDaoFacade

class DeleteRequestService(
    private val requestRepository: RequestDaoFacade
) {

    suspend fun deleteRequest(requestId: Int): Boolean {
        return requestRepository.deleteRequestById(requestId = requestId)
    }
}