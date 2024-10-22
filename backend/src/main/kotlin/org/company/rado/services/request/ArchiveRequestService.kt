package org.company.rado.services.request

import org.company.rado.dao.register.mechanic.MechanicDaoFacade
import org.company.rado.dao.request.RequestDaoFacade
import org.company.rado.dao.vehicles.VehicleDaoFacade
import io.ktor.server.plugins.*
import org.company.rado.models.requests.RequestDTO
import org.company.rado.models.requests.archive.SmallArchiveRequest
import org.company.rado.models.requests.archive.SmallArchiveRequestForMechanic
import org.company.rado.utils.toDatetime

class ArchiveRequestService(
    private val requestRepository: RequestDaoFacade,
    private val mechanicRepository: MechanicDaoFacade,
    private val vehicleRepository: VehicleDaoFacade
) {

    suspend fun archieveRequest(requestId: Int): Boolean {
        return requestRepository.archieveRequestById(requestId = requestId)
    }

    suspend fun getAllArchiveRequestsForObserver(): List<SmallArchiveRequest> {
        return requestRepository.archiveRequestsForObserver().map {
            val mechanic = mechanicRepository.findById(mechanicId = it.mechanicId!!)
                ?: throw NotFoundException("Mechanic is not found")
            SmallArchiveRequest(
                id = it.id,
                datetime = it.date?.toDatetime(time = it.time ?: "") ?: "",
                mechanicName = mechanic.fullName,
                statusRepair = it.statusRepair ?: false
            )
        }
    }

    suspend fun getAllArchiveRequestsForDriver(driverId: Int): List<SmallArchiveRequest> {
        return requestRepository.archiveRequestsForDriver(driverId = driverId).map {
            val mechanic = mechanicRepository.findById(mechanicId = it.mechanicId!!)
                ?: throw NotFoundException("Mechanic is not found")
            SmallArchiveRequest(
                id = it.id,
                datetime = it.date?.toDatetime(time = it.time ?: "") ?: "",
                mechanicName = mechanic.fullName,
                statusRepair = it.statusRepair ?: false
            )
        }
    }

    suspend fun getAllArchiveRequestsForMechanic(mechanicId: Int): List<SmallArchiveRequestForMechanic> {
        return requestRepository.archiveRequestsForMechanic(mechanicId = mechanicId).map {
            val vehicle = vehicleRepository.findVehicle(vehicleId = it.vehicleId)
                ?: throw NotFoundException("Vehicle is not found")
            SmallArchiveRequestForMechanic(
                id = it.id,
                datetime = it.date?.toDatetime(time = it.time ?: "") ?: "",
                numberVehicle = vehicle.numberVehicle,
                statusRepair = it.statusRepair ?: false
            )
        }
    }

    suspend fun getAllArchiveRequests():List<RequestDTO>{
        return requestRepository.getAllArchiveRequests()
    }
}