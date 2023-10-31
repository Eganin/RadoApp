package dao.videos

import dao.DatabaseFactory.dbQuery
import models.videos.FaultVideos
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class VideosDaoImpl : VideosDaoFacade {
    override suspend fun createVideo(requestId: Int, videoPath: String): Int? = dbQuery {
        val insertStatement = FaultVideos.insert {
            it[FaultVideos.videoPath] = videoPath
            it[FaultVideos.requestId] = requestId
        }
        insertStatement.resultedValues?.map { it[FaultVideos.id] }?.singleOrNull()
    }

    override suspend fun findVideoByRequestId(requestId: Int): List<String> = dbQuery {
        FaultVideos.select { FaultVideos.requestId eq requestId }.map { it[FaultVideos.videoPath] }
    }

    override suspend fun deleteVideos(requestId: Int): Pair<Boolean, List<String>> = dbQuery {
        val videos = FaultVideos.select { FaultVideos.requestId eq requestId }.map { it[FaultVideos.videoPath] }
        val resultDelete = FaultVideos.deleteWhere { FaultVideos.requestId eq requestId }
        Pair(resultDelete != 0, videos)
    }
}