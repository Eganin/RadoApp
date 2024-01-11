package org.company.rado.dao.request

import org.company.rado.dao.DatabaseFactory.dbQuery
import org.company.rado.models.requests.RequestDTO
import org.company.rado.models.requests.RequestDTOMapper
import org.company.rado.models.requests.Requests
import org.company.rado.models.requests.StatusRequest
import org.company.rado.utils.Mapper
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class RequestDaoImpl(override val requestDTOMapper: Mapper<RequestDTO, ResultRow> = RequestDTOMapper()) :
    RequestDaoFacade {
    override suspend fun createRequest(request: RequestDTO): Int? = dbQuery {
        val insertStatement = Requests.insert {
            it[vehicleId] = request.vehicleId
            it[driverId] = request.driverId
            it[faultDescription] = request.faultDescription
            it[statusRequest] = request.statusRequest.name
            it[arrivalDate] = request.arrivalDate
        }
        insertStatement.resultedValues?.map { it[Requests.id] }?.single()
    }

    override suspend fun recreateRequest(request: RequestDTO): Boolean = dbQuery {
        val result = Requests.update({ Requests.id eq request.id }) {
            it[vehicleId] = request.vehicleId
            it[driverId] = request.driverId
            it[statusRequest] = request.statusRequest.name
            it[faultDescription] = request.faultDescription
            it[arrivalDate] = request.arrivalDate
        }
        result != 0
    }

    override suspend fun unconfirmedRequestsForMechanic(): List<RequestDTO> = dbQuery {
        Requests.select { Requests.statusRequest eq StatusRequest.UNCONFIRMED.name }
            .map { requestDTOMapper.map(source = it) }
    }

    override suspend fun unconfirmedRequestsForDriver(driverId: Int): List<RequestDTO> = dbQuery {
        Requests.select { (Requests.statusRequest eq StatusRequest.UNCONFIRMED.name) and (Requests.driverId eq driverId) }
            .map { requestDTOMapper.map(source = it) }
    }

    override suspend fun getUnconfirmedRequestById(requestId: Int): RequestDTO? = dbQuery {
        Requests.select { (Requests.statusRequest eq StatusRequest.UNCONFIRMED.name) and (Requests.id eq requestId) }
            .map { requestDTOMapper.map(source = it) }.singleOrNull()
    }

    override suspend fun getRejectRequestById(requestId: Int): RequestDTO? = dbQuery {
        Requests.select { (Requests.id eq requestId) and (Requests.statusRequest eq StatusRequest.REJECT.name) }
            .map { requestDTOMapper.map(source = it) }.singleOrNull()
    }

    override suspend fun deleteRequestById(requestId: Int): Boolean = dbQuery {
        val resultDelete = Requests.deleteWhere { id eq requestId }
        resultDelete != 0
    }

    override suspend fun getAllActiveRequests(): List<RequestDTO> = dbQuery {
        Requests.select { Requests.statusRequest eq StatusRequest.ACTIVE.name }.map { requestDTOMapper.map(source = it) }
    }

    override suspend fun getAllArchiveRequests(): List<RequestDTO> = dbQuery {
        Requests.select { Requests.statusRequest eq StatusRequest.ARCHIVE.name }.map { requestDTOMapper.map(source = it) }
    }

    override suspend fun getAllUnconfirmedRequests(): List<RequestDTO> = dbQuery{
        Requests.select { Requests.statusRequest eq StatusRequest.UNCONFIRMED.name }.map { requestDTOMapper.map(source = it) }
    }

    override suspend fun getAllRejectRequests(): List<RequestDTO> = dbQuery {
        Requests.select { Requests.statusRequest eq StatusRequest.REJECT.name }.map { requestDTOMapper.map(source = it) }
    }

    override suspend fun confirmationRequest(
        requestId: Int,
        date: String,
        time: String,
        mechanicId: Int,
        streetRepair: String
    ): Boolean =
        dbQuery {
            val result =
                Requests.update({ (Requests.id eq requestId) and (Requests.statusRequest eq StatusRequest.UNCONFIRMED.name) }) {
                    it[Requests.time] = time
                    it[Requests.date] = date
                    it[Requests.mechanicId] = mechanicId
                    it[statusRequest] = StatusRequest.ACTIVE.name
                    it[statusRepair] = false
                    it[Requests.streetRepair]=streetRepair
                }
            result != 0
        }

    override suspend fun activeRequestsForMechanic(
        mechanicId: Int,
        date: String
    ): List<RequestDTO> = dbQuery {
        Requests.select {
            if (date.isNotEmpty()) {
                (Requests.statusRequest eq StatusRequest.ACTIVE.name) and
                        (Requests.mechanicId eq mechanicId) and
                        (Requests.date eq date)
            } else {
                (Requests.statusRequest eq StatusRequest.ACTIVE.name) and
                        (Requests.mechanicId eq mechanicId)
            }
        }.map { requestDTOMapper.map(source = it) }
    }

    override suspend fun activeRequestsForDriver(driverId: Int, date: String): List<RequestDTO> =
        dbQuery {
            Requests.select {
                if (date.isNotEmpty()) {
                    (Requests.statusRequest eq StatusRequest.ACTIVE.name) and
                            (Requests.driverId eq driverId) and
                            (Requests.date eq date)
                } else {
                    (Requests.statusRequest eq StatusRequest.ACTIVE.name) and
                            (Requests.driverId eq driverId)
                }
            }.map { requestDTOMapper.map(source = it) }
        }

    override suspend fun activeRequestsForObserver(date: String): List<RequestDTO> = dbQuery {
        Requests.select {
            if (date.isNotEmpty()) {
                (Requests.statusRequest eq StatusRequest.ACTIVE.name) and
                        (Requests.date eq date)
            } else {
                (Requests.statusRequest eq StatusRequest.ACTIVE.name)
            }
        }.map { requestDTOMapper.map(source = it) }
    }

    override suspend fun getActiveRequestById(requestId: Int): RequestDTO? = dbQuery {
        Requests.select { (Requests.statusRequest eq StatusRequest.ACTIVE.name) and (Requests.id eq requestId) }
            .map { requestDTOMapper.map(source = it) }.singleOrNull()
    }

    override suspend fun archieveRequestById(requestId: Int): Boolean = dbQuery {
        val result =
            Requests.update({ (Requests.id eq requestId) and (Requests.statusRequest eq StatusRequest.ACTIVE.name) }) {
                it[statusRequest] = StatusRequest.ARCHIVE.name
                it[statusRepair] = true
            }
        result != 0
    }

    override suspend fun getArchiveRequestById(requestId: Int): RequestDTO? = dbQuery {
        Requests.select { (Requests.statusRequest eq StatusRequest.ARCHIVE.name) and (Requests.id eq requestId) }
            .map { requestDTOMapper.map(source = it) }.singleOrNull()
    }

    override suspend fun archiveRequestsForObserver(): List<RequestDTO> = dbQuery {
        Requests.select { Requests.statusRequest eq StatusRequest.ARCHIVE.name }
            .map { requestDTOMapper.map(source = it) }
    }

    override suspend fun archiveRequestsForDriver(driverId: Int): List<RequestDTO> = dbQuery {
        Requests.select { (Requests.statusRequest eq StatusRequest.ARCHIVE.name) and (Requests.driverId eq driverId) }
            .map { requestDTOMapper.map(source = it) }
    }

    override suspend fun archiveRequestsForMechanic(mechanicId: Int): List<RequestDTO> = dbQuery {
        Requests.select { (Requests.statusRequest eq StatusRequest.ARCHIVE.name) and (Requests.mechanicId eq mechanicId) }
            .map { requestDTOMapper.map(source = it) }
    }

    override suspend fun updateRejectRequestById(
        requestId: Int,
        commentMechanic: String,
        mechanicId: Int
    ): Boolean =
        dbQuery {
            val result =
                Requests.update({ (Requests.id eq requestId) and (Requests.statusRequest eq StatusRequest.UNCONFIRMED.name) }) {
                    it[statusRequest] = StatusRequest.REJECT.name
                    it[Requests.commentMechanic] = commentMechanic
                    it[Requests.mechanicId] = mechanicId
                }
            result != 0
        }

    override suspend fun rejectedRequestForObserver(): List<RequestDTO> = dbQuery {
        Requests.select { Requests.statusRequest eq StatusRequest.REJECT.name }
            .map { requestDTOMapper.map(source = it) }
    }

    override suspend fun rejectedRequestForDriver(driverId: Int): List<RequestDTO> = dbQuery {
        Requests.select { (Requests.statusRequest eq StatusRequest.REJECT.name) and (Requests.driverId eq driverId) }
            .map { requestDTOMapper.map(source = it) }
    }
}