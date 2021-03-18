package com.acompany.lift.common

import android.content.Context
import android.media.MediaPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SoundManager @Inject constructor(@ApplicationContext val context: Context) {

    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }

    enum class Sound(val path: String) {
        Tone("tone.mp3")
    }

    fun playSound(sound: Sound) {
        context.assets.openFd(sound.path).use { afd ->
            mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        }
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }
        mediaPlayer.prepareAsync()
    }
}