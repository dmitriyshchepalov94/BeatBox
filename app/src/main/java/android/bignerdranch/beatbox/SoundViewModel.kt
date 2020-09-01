package android.bignerdranch.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class SoundViewModel(val beatBox: BeatBox): BaseObservable(){
    var sound: Sound? = null
        get() {return field}
        set(value) {
            field = value
            notifyChange()}
    fun getName(): String
    {
        return sound?.name!!
    }

    fun onButtonClicked() {
        sound?.let { beatBox.play(it) }
    }

}