package services

import dao.vehicles.VehicleDaoFacade

class VehicleService(
    private val vehicleRepository: VehicleDaoFacade
) {

    suspend fun deleteVehicle(numberVehicle: String, typeVehicle: String): Boolean {
        return vehicleRepository.deleteVehicle(numberVehicle=numberVehicle,typeVehicle=typeVehicle)
    }
}