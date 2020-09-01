package android.bignerdranch.beatbox

class Sound(val path: String) {

    var name: String? = null
    var id: Int? = null
    init {
        val components = path.split("/")
        val filename = components[components.size - 1]
        name = filename.replace(".wav", "")
    }
}