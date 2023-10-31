package services.request

import dao.images.ImagesDaoFacade
import dao.register.driver.DriverDaoFacade
import dao.request.RequestDaoFacade
import dao.vehicles.VehicleDaoFacade
import io.ktor.server.plugins.*
import models.requests.unconfirmed.FullUnconfirmedRequest
import models.requests.unconfirmed.SmallUnconfirmedRequest

class UnconfirmedRequestService(
    private val requestRepository: RequestDaoFacade,
    private val vehicleRepository: VehicleDaoFacade,
    private val driverRepository: DriverDaoFacade,
    private val imageRepository: ImagesDaoFacade
) {

    suspend fun getUnconfirmedRequests(driverId: Int? = null): List<SmallUnconfirmedRequest> {
        val requests = if (driverId != null) {
            requestRepository.unconfirmedRequestsForDriver(driverId = driverId)
        } else {
            requestRepository.unconfirmedRequestsForMechanic()
        }
        return requests.map {
            val vehicleDTO = vehicleRepository.findVehicle(vehicleId = it.vehicleId)
                ?: throw NotFoundException("Vehicle is not found")

            SmallUnconfirmedRequest(
                id = it.id,
                vehicleType = vehicleDTO.typeVehicle,
                vehicleNumber = vehicleDTO.numberVehicle,
                statusRequest = it.statusRequest
            )
        }
    }

    suspend fun getFullUnconfirmedRequest(requestId: Int): FullUnconfirmedRequest {
        val requestDTO = requestRepository.getUnconfirmedRequestById(requestId = requestId)
            ?: throw NotFoundException("Request is not found")

        val vehicleDTO = vehicleRepository.findVehicle(vehicleId = requestDTO.vehicleId)
            ?: throw NotFoundException("Vehicle is not found")
        val driverDTO = driverRepository.fundById(driverId = requestDTO.driverId)
            ?: throw NotFoundException("Driver is not found")
        val images = imageRepository.findByRequestId(requestId = requestDTO.id)

        return FullUnconfirmedRequest(
            id = requestDTO.id,
            vehicleType = vehicleDTO.typeVehicle,
            vehicleNumber = vehicleDTO.numberVehicle,
            driverUsername = driverDTO.fullName,
            driverPhone = driverDTO.phone,
            statusRequest = requestDTO.statusRequest,
            faultDescription = requestDTO.faultDescription,
            images = images
        )
    }
}