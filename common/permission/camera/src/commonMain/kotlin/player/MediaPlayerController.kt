package player

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect class MediaPlayerController {

    fun prepare(pathSource: String, listener: MediaPlayerListener)

    fun start()

    fun pause()

    fun stop()

    fun isPlaying(): Boolean

    fun release()
}