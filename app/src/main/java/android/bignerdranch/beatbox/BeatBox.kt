package android.bignerdranch.beatbox

import android.content.Context
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

open class BeatBox(val context: Context) {
    private var assets: AssetManager? = null
    private var soundPool = SoundPool(MAX_SOUND, AudioManager.STREAM_MUSIC, 0)
    var sounds = mutableListOf<Sound>()
    var speed = 1.0F
    companion object
    {
        val TAG = "BeatBox"
        val SOUNDS_FOLDER = "sample_sound"
        val MAX_SOUND = 5
    }

    init {
        assets = context.assets
        loadSound()
    }

    private fun load(sound:Sound)
    {
        val afd = assets?.openFd(sound.path)
        val id = soundPool.load(afd, 1)
        sound.id = id
    }


    fun play(sound: Sound)
    {
        sound.id?.let { soundPool.play(it, 1.0F, 1.0F, 1, 0, speed) }
    }


    fun release()
    {
        soundPool.release()
    }

    private fun loadSound()
    {
        var soundNames: Array<out String>? = null
        try {
            soundNames = assets?.list(SOUNDS_FOLDER)
            Log.i(TAG, "Found ${soundNames?.size} sounds")
        }
        catch (ioe: IOException)
        {
            Log.e(TAG, "Could not list assets", ioe)
            return
        }

        soundNames?.forEach {
            try {
                val path = "$SOUNDS_FOLDER/$it"
                val sound = Sound(path)
                load(sound)
                sounds.add(sound)
            }
            catch (ioe: IOException)
            {
                Log.e(TAG, "Couldn't load sound", ioe)
            }

        }

    }
}