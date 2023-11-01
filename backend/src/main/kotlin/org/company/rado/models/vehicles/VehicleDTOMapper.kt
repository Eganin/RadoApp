package org.company.rado.models.vehicles

import org.jetbrains.exposed.sql.ResultRow
import org.company.rado.utils.Mapper

class VehicleDTOMapper : Mapper<VehicleDTO, ResultRow> {
    override fun map(source: ResultRow): VehicleDTO {
        return VehicleDTO(
            typeVehicle = source[Vehicles.typeVehicle],
            numberVehicle = source[Vehicles.numberVehicle]
        )
    }
}