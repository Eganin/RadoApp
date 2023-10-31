package dao.vehicles

import models.vehicles.VehicleDTO
import org.jetbrains.exposed.sql.ResultRow
import utils.Mapper

interface VehicleDaoFacade {


    val mapper: Mapper<VehicleDTO, ResultRow>
    suspend fun createVehicle(numberVehicle: String, typeVehicle: String): Int?

    suspend fun findVehicle(vehicleId: Int): VehicleDTO?

    suspend fun findVehicleForRecreateRequest(numberVehicle: String, typeVehicle: String): Int?

    suspend fun deleteVehicle(numberVehicle: String, typeVehicle: String): Boolean
}