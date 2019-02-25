package quantum.api.core

import com.runemate.game.api.script.framework.task.Task
import com.runemate.game.api.script.framework.task.TaskBot
import java.util.*

open class ExtendedTaskBot : TaskBot(), Iterable<Task> {

    fun <T : Task> findTask(taskClass: Class<T>) = firstOrNull { taskClass.isInstance(it) }?.let { it as T }

    override fun iterator() = object : Iterator<Task> {
        val taskStack = Stack<Task>()

        init {
            (tasks.size downTo 1).forEach { i -> taskStack.push(tasks[i]) }
        }

        override fun hasNext() = !taskStack.isEmpty()

        override fun next() = taskStack.pop().let {
            (it.children.size downTo 1).forEach { i ->
                taskStack.push(it.children[i])
            }
            it
        }
    }
}
