package dao.request

import models.requests.RequestDTO
import org.jetbrains.exposed.sql.ResultRow
import utils.Mapper

interface RequestDaoFacade {

    val requestDTOMapper: Mapper<RequestDTO, ResultRow>

    suspend fun createRequest(request: RequestDTO): Int?

    suspend fun recreateRequest(request:RequestDTO): Boolean

    suspend fun unconfirmedRequestsForMechanic(): List<RequestDTO>

    suspend fun unconfirmedRequestsForDriver(driverId: Int): List<RequestDTO>

    suspend fun confirmationRequest(requestId: Int, date: String, time: String, mechanicId: Int): Boolean

    suspend fun activeRequestsForMechanic(mechanicId: Int, date: String): List<RequestDTO>

    suspend fun activeRequestsForDriver(driverId: Int, date: String): List<RequestDTO>

    suspend fun activeRequestsForObserver(date: String): List<RequestDTO>

    suspend fun getActiveRequestById(requestId: Int): RequestDTO?

    suspend fun archieveRequestById(requestId: Int): Boolean

    suspend fun getArchiveRequestById(requestId: Int): RequestDTO?

    suspend fun archiveRequestsForObserver(): List<RequestDTO>

    suspend fun archiveRequestsForDriver(driverId: Int): List<RequestDTO>

    suspend fun archiveRequestsForMechanic(mechanicId: Int): List<RequestDTO>

    suspend fun updateRejectRequestById(requestId: Int,commentMechanic:String,mechanicId: Int): Boolean

    suspend fun rejectedRequestForObserver(): List<RequestDTO>

    suspend fun rejectedRequestForDriver(driverId: Int): List<RequestDTO>

    suspend fun getUnconfirmedRequestById(requestId: Int): RequestDTO?

    suspend fun getRejectRequestById(requestId: Int): RequestDTO?

    suspend fun deleteRequestById(requestId: Int): Boolean
}