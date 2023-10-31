package models.vehicles

import org.jetbrains.exposed.sql.Table

object Vehicles : Table(name = "vehicles") {
    val id = integer(name = "id").autoIncrement()
    val typeVehicle = varchar(name = "type_vehicle", length = 20)
    val numberVehicle = varchar(name = "number_vehicle", length = 20)

    override val primaryKey = PrimaryKey(id)
}