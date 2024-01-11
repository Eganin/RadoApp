package org.company.rado.services

import org.company.rado.dao.vehicles.VehicleDaoFacade
import org.company.rado.models.vehicles.VehicleDTO

class VehicleService(
    private val vehicleRepository: VehicleDaoFacade
) {

    suspend fun deleteVehicle(numberVehicle: String, typeVehicle: String): Boolean {
        return vehicleRepository.deleteVehicle(numberVehicle=numberVehicle,typeVehicle=typeVehicle)
    }

    suspend fun allVehicles():List<VehicleDTO>{
        return vehicleRepository.allVehicles()
    }
}