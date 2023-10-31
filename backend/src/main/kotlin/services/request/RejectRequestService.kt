package services.request

import dao.images.ImagesDaoFacade
import dao.register.mechanic.MechanicDaoFacade
import dao.request.RequestDaoFacade
import dao.vehicles.VehicleDaoFacade
import io.ktor.server.plugins.*
import models.requests.reject.FullRejectRequest
import models.requests.reject.SmallRejectRequest

class RejectRequestService(
    private val requestRepository: RequestDaoFacade,
    private val vehicleRepository: VehicleDaoFacade,
    private val mechanicRepository: MechanicDaoFacade,
    private val imageRepository: ImagesDaoFacade
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

    suspend fun getRejectRequestById(requestId: Int): FullRejectRequest {
        val request = requestRepository.getRejectRequestById(requestId = requestId)
            ?: throw NotFoundException("Request is not found")
        val vehicleDTO = vehicleRepository.findVehicle(vehicleId = request.vehicleId)
            ?: throw NotFoundException("Vehicle is not found")
        val images = imageRepository.findByRequestId(requestId=request.id)
        return FullRejectRequest(
            id = request.id,
            typeVehicle = vehicleDTO.typeVehicle,
            numberVehicle = vehicleDTO.numberVehicle,
            faultDescription = request.faultDescription,
            images = images,
            driverId = request.driverId
        )
    }
}