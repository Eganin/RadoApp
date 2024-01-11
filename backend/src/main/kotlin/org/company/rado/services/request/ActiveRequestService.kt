package org.company.rado.services.request

import org.company.rado.dao.register.mechanic.MechanicDaoFacade
import org.company.rado.dao.request.RequestDaoFacade
import org.company.rado.dao.vehicles.VehicleDaoFacade
import io.ktor.server.plugins.*
import org.company.rado.models.requests.RequestDTO
import org.company.rado.models.requests.active.SmallActiveRequestForDriver
import org.company.rado.models.requests.active.SmallActiveRequestForMechanic
import org.company.rado.models.requests.active.SmallActiveRequestForObserver
import org.company.rado.utils.toDatetime

class ActiveRequestService(
    private val requestRepository: RequestDaoFacade,
    private val vehicleRepository: VehicleDaoFacade,
    private val mechanicRepository: MechanicDaoFacade
) {
    suspend fun confirmationRequest(requestId: Int, date: String, time: String, mechanicId: Int,streetRepair:String): Boolean {
        return requestRepository.confirmationRequest(
            requestId = requestId,
            time = time,
            date = date,
            mechanicId = mechanicId,
            streetRepair = streetRepair
        )
    }

    suspend fun activeRequestsForMechanic(mechanicId: Int, date: String): List<SmallActiveRequestForMechanic> {
        return requestRepository.activeRequestsForMechanic(mechanicId = mechanicId, date = date).map {
            val vehicleDTO = vehicleRepository.findVehicle(vehicleId = it.vehicleId)
                ?: throw NotFoundException("Vehicle is not found")
            SmallActiveRequestForMechanic(
                id=it.id,
                typeVehicle = vehicleDTO.typeVehicle,
                numberVehicle = vehicleDTO.numberVehicle,
                datetime = it.date?.toDatetime(time = it.time ?: "")?:""
            )
        }

    }

    suspend fun activeRequestsForDriver(driverId: Int, date: String): List<SmallActiveRequestForDriver> {
        return requestRepository.activeRequestsForDriver(driverId = driverId, date = date).map {
            val mechanicFullName = mechanicRepository.findById(
                mechanicId = it.mechanicId!!
            )?.fullName ?: ""
            SmallActiveRequestForDriver(
                id=it.id,
                mechanicName = mechanicFullName,
                datetime = it.date?.toDatetime(time = it.time ?: "")?:""
            )
        }
    }

    suspend fun activeRequestsForObserver(date: String): List<SmallActiveRequestForObserver> {
        return requestRepository.activeRequestsForObserver(date = date).map {
            val mechanic = mechanicRepository.findById(
                mechanicId = it.mechanicId!!
            ) ?: throw NotFoundException("Not found mechanic by id")
            val vehicleDTO = vehicleRepository.findVehicle(vehicleId = it.vehicleId)
                ?: throw NotFoundException("Vehicle is not found")

            SmallActiveRequestForObserver(
                id=it.id,
                typeVehicle = vehicleDTO.typeVehicle,
                numberVehicle = vehicleDTO.numberVehicle,
                mechanicName = mechanic.fullName,
                datetime = it.date?.toDatetime(time = it.time ?: "")?:""
            )
        }
    }

    suspend fun getAllActiveRequests():List<RequestDTO>{
        return requestRepository.getAllActiveRequests()
    }
}