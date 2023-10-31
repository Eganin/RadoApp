package models.faultimages

import models.requests.Requests
import org.jetbrains.exposed.sql.Table

object FaultImages : Table(name = "fault_images") {
    val id = integer(name = "id").autoIncrement()
    val imagePath = varchar(name = "image_path", length = 100)
    val requestId = reference(name = "request_id", refColumn = Requests.id)

    override val primaryKey = PrimaryKey(id)
}