package models.vehicles

import org.jetbrains.exposed.sql.ResultRow
import utils.Mapper

class VehicleDTOMapper : Mapper<VehicleDTO,ResultRow> {
    override fun map(source: ResultRow): VehicleDTO {
        return VehicleDTO(
            typeVehicle = source[Vehicles.typeVehicle],
            numberVehicle = source[Vehicles.numberVehicle]
        )
    }
}