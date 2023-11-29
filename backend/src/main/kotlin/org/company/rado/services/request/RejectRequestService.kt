package org.company.rado.services.request

import io.ktor.server.plugins.NotFoundException
import org.company.rado.dao.register.mechanic.MechanicDaoFacade
import org.company.rado.dao.request.RequestDaoFacade
import org.company.rado.dao.vehicles.VehicleDaoFacade
import org.company.rado.models.requests.reject.SmallRejectRequest

class RejectRequestService(
    private val requestRepository: RequestDaoFacade,
    private val vehicleRepository: VehicleDaoFacade,
    private val mechanicRepository: MechanicDaoFacade
) {
    suspend fun rejectRequest(requestId: Int, commentMechanic: String, mechanicId: Int): Boolean {
        return requestRepository.updateRejectRequestById(
            requestId = requestId,
            commentMechanic = commentMechanic,
            mechanicId = mechanicId
        )
    }

    suspend fun getAllRejectRequest(driverId: Int? = null): List<SmallRejectRequest> {
        val requests =
            if (driverId == null) requestRepository.rejectedRequestForObserver()
            else requestRepository.rejectedRequestForDriver(driverId = driverId)
        return requests.map {
            val vehicleDTO = vehicleRepository.findVehicle(vehicleId = it.vehicleId)
                ?: throw NotFoundException("Vehicle is not found")
            val mechanicDTO = mechanicRepository.findById(mechanicId = it.mechanicId!!)
                ?: throw NotFoundException("Mechanic is not found")
            SmallRejectRequest(
                id = it.id,
                numberVehicle = vehicleDTO.numberVehicle,
                mechanicName = mechanicDTO.fullName
            )
        }
    }
}