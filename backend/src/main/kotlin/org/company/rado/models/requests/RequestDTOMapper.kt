package org.company.rado.models.requests

import org.jetbrains.exposed.sql.ResultRow
import org.company.rado.utils.Mapper

class RequestDTOMapper : Mapper<RequestDTO, ResultRow> {
    override fun map(source: ResultRow): RequestDTO {
        return RequestDTO(
            id = source[Requests.id],
            vehicleId = source[Requests.vehicleId],
            driverId = source[Requests.driverId],
            statusRequest = stringToStatusRequest(value = source[Requests.statusRequest]),
            faultDescription = source[Requests.faultDescription] ?: "",
            mechanicId = source[Requests.mechanicId],
            time = source[Requests.time],
            date= source[Requests.date],
            statusRepair = source[Requests.statusRepair],
            commentMechanic = source[Requests.commentMechanic]
        )
    }

    private fun stringToStatusRequest(value: String): StatusRequest {
        return when (value) {
            StatusRequest.UNCONFIRMED.name -> StatusRequest.UNCONFIRMED
            StatusRequest.ACTIVE.name -> StatusRequest.ACTIVE
            StatusRequest.ARCHIVE.name -> StatusRequest.ARCHIVE
            StatusRequest.REJECT.name -> StatusRequest.REJECT
            else -> StatusRequest.UNCONFIRMED
        }
    }
}