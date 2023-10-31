package dao.vehicles

import dao.DatabaseFactory.dbQuery
import models.vehicles.VehicleDTO
import models.vehicles.VehicleDTOMapper
import models.vehicles.Vehicles
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import utils.Mapper

class VehicleDaoImpl(override val mapper: Mapper<VehicleDTO, ResultRow> = VehicleDTOMapper()) : VehicleDaoFacade {
    override suspend fun createVehicle(numberVehicle: String, typeVehicle: String): Int? = dbQuery {
        val insertStatement = Vehicles.insert {
            it[Vehicles.typeVehicle] = typeVehicle
            it[Vehicles.numberVehicle] = numberVehicle
        }

        insertStatement.resultedValues?.map { it[Vehicles.id] }?.singleOrNull()
    }

    override suspend fun findVehicle(vehicleId: Int): VehicleDTO? = dbQuery {
        Vehicles.select { Vehicles.id eq vehicleId }
            .map { mapper.map(source = it) }.singleOrNull()
    }

    override suspend fun findVehicleForRecreateRequest(numberVehicle: String, typeVehicle: String): Int? = dbQuery {
        Vehicles.select { (Vehicles.numberVehicle eq numberVehicle) and (Vehicles.typeVehicle eq typeVehicle) }
            .map { it[Vehicles.id] }.singleOrNull()
    }

    override suspend fun deleteVehicle(numberVehicle: String, typeVehicle: String): Boolean = dbQuery {
        Vehicles.deleteWhere { (Vehicles.numberVehicle eq numberVehicle) and (Vehicles.typeVehicle eq typeVehicle) } != 0
    }
}