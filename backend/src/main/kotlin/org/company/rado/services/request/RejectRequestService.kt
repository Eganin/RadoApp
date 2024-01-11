package org.company.rado.services.request

import io.ktor.server.plugins.NotFoundException
import org.company.rado.dao.images.ImagesDaoFacade
import org.company.rado.dao.register.mechanic.MechanicDaoFacade
import org.company.rado.dao.request.RequestDaoFacade
import org.company.rado.dao.vehicles.VehicleDaoFacade
import org.company.rado.dao.videos.VideosDaoFacade
import org.company.rado.models.requests.RequestDTO
import org.company.rado.models.requests.reject.FullRejectRequest
import org.company.rado.models.requests.reject.SmallRejectRequest

class RejectRequestService(
    private val requestRepository: RequestDaoFacade,
    private val vehicleRepository: VehicleDaoFacade,
    private val mechanicRepository: MechanicDaoFacade,
    private val imageRepository: ImagesDaoFacade,
    private val videoRepository: VideosDaoFacade
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
        val videos = videoRepository.findVideoByRequestId(requestId=request.id)
        return FullRejectRequest(
            id = request.id,
            typeVehicle = vehicleDTO.typeVehicle,
            numberVehicle = vehicleDTO.numberVehicle,
            faultDescription = request.faultDescription,
            images = images,
            videos=videos,
            driverId = request.driverId,
            commentMechanic = request.commentMechanic?:""
        )
    }

    suspend fun getAllRejectRequestsIntegration():List<RequestDTO>{
        return requestRepository.getAllRejectRequests()
    }
}