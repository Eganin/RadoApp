package models.videos

import models.requests.Requests
import org.jetbrains.exposed.sql.Table

object FaultVideos : Table(name = "fault_videos") {
    val id = integer(name = "id").autoIncrement()
    val videoPath = varchar(name = "video_path", length = 100)
    val requestId = reference(name = "request_id", refColumn = Requests.id)

    override val primaryKey = PrimaryKey(id)
}