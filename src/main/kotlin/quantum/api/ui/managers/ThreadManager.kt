package quantum.api.ui.managers

import com.runemate.game.api.script.framework.core.LoopingThread

class ThreadManager {

    private var thread = LoopingThread(this::invokeAll, 2000)
    private var functionList = mutableListOf<() -> Unit>()

    init {
        thread.start()
    }

    fun add(function: () -> Unit) {
        functionList.add(function)
    }

    private fun invokeAll() {
        functionList.forEach {
            it.invoke()
        }
    }
}