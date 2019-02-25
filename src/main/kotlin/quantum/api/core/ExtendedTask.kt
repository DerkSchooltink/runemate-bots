package quantum.api.core

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.framework.task.Task
import com.runemate.game.api.script.framework.task.TaskBot
import java.util.concurrent.Callable

open class ExtendedTask<T : TaskBot>(
        private val validate: Callable<Boolean>?,
        private val execute: Runnable?,
        vararg children: Task
) : Task(*children) {
    val local by lazy { Players.getLocal() }
    val bot: T by lazy { Environment.getBot() as T }

    constructor(vararg children: Task) : this(null, null, *children)

    constructor(validate: Callable<Boolean>, vararg children: Task) : this(validate, null, *children)

    constructor(execute: Runnable, vararg children: Task) : this(null, execute, *children)

    override fun validate() = validate?.let {
        bot.logger.info("Validating $it for task $this")
        try {
            it.call()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    } ?: false

    override fun execute() {
        execute?.run()
    }
}