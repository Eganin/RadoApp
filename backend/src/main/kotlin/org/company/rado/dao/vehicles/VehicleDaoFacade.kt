package org.company.rado.dao.vehicles

import org.company.rado.models.vehicles.VehicleDTO
import org.jetbrains.exposed.sql.ResultRow
import org.company.rado.utils.Mapper

interface VehicleDaoFacade {


    val mapper: Mapper<VehicleDTO, ResultRow>
    suspend fun createVehicle(numberVehicle: String, typeVehicle: String): Int?

    suspend fun findVehicle(vehicleId: Int): VehicleDTO?

    suspend fun findVehicleForRecreateRequest(numberVehicle: String, typeVehicle: String): Int?

    suspend fun deleteVehicle(numberVehicle: String, typeVehicle: String): Boolean
}