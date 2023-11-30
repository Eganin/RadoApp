package org.company.rado.services.request

import org.company.rado.dao.images.ImagesDaoFacade
import org.company.rado.dao.register.driver.DriverDaoFacade
import org.company.rado.dao.register.mechanic.MechanicDaoFacade
import org.company.rado.dao.request.RequestDaoFacade
import org.company.rado.dao.vehicles.VehicleDaoFacade
import io.ktor.server.plugins.*
import org.company.rado.dao.videos.VideosDaoFacade
import org.company.rado.models.requests.FullRequest
import org.company.rado.models.requests.RequestDTO
import org.company.rado.models.requests.StatusRequest
import org.company.rado.models.requests.create.CreateRequestResponse

class RequestService(
    private val requestRepository: RequestDaoFacade,
    private val vehicleRepository: VehicleDaoFacade,
    private val mechanicRepository: MechanicDaoFacade,
    private val driverRepository: DriverDaoFacade,
    private val imageRepository: ImagesDaoFacade,
    private val videoRepository : VideosDaoFacade
) {

    suspend fun createRequest(
        driverUsername: String,
        typeVehicle: String,
        numberVehicle: String,
        faultDescription: String,
        arrivalDate:String
    ): CreateRequestResponse {
        val vehicleId = vehicleRepository.createVehicle(numberVehicle = numberVehicle, typeVehicle = typeVehicle)
            ?: throw NotFoundException("Vehicle is not found")
        val driverId = driverRepository.findByFullName(username = driverUsername)?.id
            ?: throw NotFoundException("Driver is not found")

        val requestDTO = RequestDTO(
            vehicleId = vehicleId,
            driverId = driverId,
            statusRequest = StatusRequest.UNCONFIRMED,
            faultDescription = faultDescription,
            arrivalDate = arrivalDate
        )
        val requestId = requestRepository.createRequest(request = requestDTO)

        return if (requestId != null) {
            CreateRequestResponse(id = requestId)
        } else {
            CreateRequestResponse(id = -1)
        }
    }

    suspend fun recreateRequest(
        requestId: Int,
        driverId: Int,
        typeVehicle: String,
        numberVehicle: String,
        oldTypeVehicle: String,
        oldNumberVehicle: String,
        faultDescription: String,
        arrivalDate: String
    ): CreateRequestResponse? {
        var removeVehicle = false
        val vehicleId = if (typeVehicle == oldTypeVehicle && numberVehicle == oldNumberVehicle) {
            vehicleRepository.findVehicleForRecreateRequest(numberVehicle = numberVehicle, typeVehicle = typeVehicle)
                ?: throw NotFoundException("Vehicle is not found")
        } else {
            removeVehicle = true
            (vehicleRepository.createVehicle(numberVehicle = numberVehicle, typeVehicle = typeVehicle)
                ?: throw NotFoundException("Vehicle is not found"))
        }

        val requestDTO = RequestDTO(
            id = requestId,
            vehicleId = vehicleId,
            driverId = driverId,
            statusRequest = StatusRequest.UNCONFIRMED,
            faultDescription = faultDescription,
            arrivalDate=arrivalDate
        )
        val requestIsRecreate = requestRepository.recreateRequest(request = requestDTO)
        if (removeVehicle) try {
            vehicleRepository.deleteVehicle(numberVehicle = numberVehicle, typeVehicle = typeVehicle)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return if (requestIsRecreate) {
            CreateRequestResponse(id = requestId)
        } else {
            null
        }
    }

    suspend fun getFullRequest(requestId: Int, requestType: StatusRequest): FullRequest {
        val requestDTO = when (requestType) {
            StatusRequest.ARCHIVE -> {
                requestRepository.getArchiveRequestById(requestId = requestId)
                    ?: throw NotFoundException("Request is not found")
            }

            StatusRequest.ACTIVE -> {
                requestRepository.getActiveRequestById(requestId = requestId)
                    ?: throw NotFoundException("Request is not found")
            }

            StatusRequest.REJECT->{
                requestRepository.getRejectRequestById(requestId=requestId)
                    ?: throw NotFoundException("Request is not found")
            }

            else -> {
                throw NotFoundException("Request is not found")
            }
        }
        val vehicleDTO = vehicleRepository.findVehicle(vehicleId = requestDTO.vehicleId)
            ?: throw NotFoundException("Vehicle is not found")
        val driver = driverRepository.findById(driverId = requestDTO.driverId)
            ?: throw NotFoundException("Driver is not found")
        val mechanic = mechanicRepository.findById(mechanicId = requestDTO.mechanicId!!)
            ?: throw NotFoundException("Mechanic is not found")
        val images = imageRepository.findByRequestId(requestId = requestDTO.id)
        val videos = videoRepository.findVideoByRequestId(requestId=requestDTO.id)

        return FullRequest(
            id = requestDTO.id,
            vehicleNumber = vehicleDTO.numberVehicle,
            vehicleType = vehicleDTO.typeVehicle,
            driverName = driver.fullName,
            driverPhone = driver.phone,
            statusRequest = requestDTO.statusRequest,
            faultDescription = requestDTO.faultDescription,
            mechanicName = mechanic.fullName,
            mechanicPhone = mechanic.phone,
            date = requestDTO.date ?: "",
            time = requestDTO.time ?: "",
            statusRepair = requestDTO.statusRepair ?: false,
            images = images,
            videos = videos,
            arrivalDate = requestDTO.arrivalDate,
            streetRepair = requestDTO.streetRepair
        )
    }
}