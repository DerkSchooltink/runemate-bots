package quantum.api.ui.managers

class ListenerManager {

    private var lastFreeze: Long = 1200

    fun freezeListener() {
        lastFreeze = System.currentTimeMillis()
    }

    fun freezeListener(duration: Long) {
        lastFreeze = System.currentTimeMillis() + duration
    }

    var getLastFreeze: Long = lastFreeze
}