package org.company.rado.dao.videos

interface VideosDaoFacade {

    suspend fun createVideo(requestId: Int, videoPath: String): Int?

    suspend fun findVideoByRequestId(requestId: Int):List<String>

    suspend fun deleteVideos(requestId: Int): Pair<Boolean,List<String>>

    suspend fun deleteVideoByName(videoName:String): Pair<Boolean,String>
}