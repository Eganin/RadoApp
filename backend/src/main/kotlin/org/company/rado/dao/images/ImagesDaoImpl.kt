package org.company.rado.dao.images

import org.company.rado.dao.DatabaseFactory.dbQuery
import org.company.rado.models.faultimages.FaultImages
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class ImagesDaoImpl : ImagesDaoFacade {
    override suspend fun createImage(requestId: Int, imagePath: String): Int? = dbQuery {
        val insertStatement = FaultImages.insert {
            it[FaultImages.imagePath] = imagePath
            it[FaultImages.requestId] = requestId
        }
        insertStatement.resultedValues?.map { it[FaultImages.id] }?.singleOrNull()
    }

    override suspend fun findByRequestId(requestId: Int): List<String> = dbQuery {
        FaultImages.select { FaultImages.requestId eq requestId }
            .map { it[FaultImages.imagePath] }
    }

    override suspend fun deleteImages(requestId: Int): Pair<Boolean, List<String>> = dbQuery {
        val images = FaultImages.select { FaultImages.requestId eq requestId }
            .map { it[FaultImages.imagePath] }
        val resultDelete = FaultImages.deleteWhere { FaultImages.requestId eq requestId }
        Pair(resultDelete != 0, images)
    }
}