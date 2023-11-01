package org.company.rado.dao.images

interface ImagesDaoFacade {

    suspend fun createImage(requestId: Int, imagePath: String): Int?

    suspend fun findByRequestId(requestId: Int): List<String>

    suspend fun deleteImages(requestId: Int): Pair<Boolean,List<String>>
}