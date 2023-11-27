package org.company.rado.models.requests

import org.company.rado.models.users.Drivers
import org.company.rado.models.users.Mechanics
import org.company.rado.models.vehicles.Vehicles
import org.jetbrains.exposed.sql.Table

object Requests : Table(name = "requests") {
    val id = integer(name = "id").autoIncrement()
    val vehicleId = reference(name = "vehicle_id", refColumn = Vehicles.id)
    val driverId = reference(name = "driver_id", refColumn = Drivers.id)
    val mechanicId = reference(name = "mechanic_id", refColumn = Mechanics.id).nullable()
    val date = varchar(name = "date", length = 10).nullable()
    val time = varchar(name = "time", length = 5).nullable()
    val arrivalDate = varchar(name="arrival_date",length=10).nullable()
    val faultDescription = text(name = "fault_description").nullable()
    val statusRepair = bool(name = "status_repair").nullable()
    val commentMechanic = text(name = "comment_mechanic").nullable()
    val statusRequest = varchar(name = "status_request", length = 20)

    override val primaryKey = PrimaryKey(id)
}