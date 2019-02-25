package quantum.api.framework.utils

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.util.Resources
import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*
import javax.imageio.ImageIO

object Resources {

    fun writeToFile(resource: String): File? {
        val resourceStream = Resources.getAsStream(resource)
        val temp = File(Environment.getStorageDirectory().absolutePath
                + File.separator
                + Arrays.stream(resource.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()).reduce { _, s -> s }.get())
        try {
            Files.copy(resourceStream, temp.toPath(), StandardCopyOption.REPLACE_EXISTING)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return temp
    }

    fun imageFromURL(path: String): Image {
        val url = URL(path)
        return SwingFXUtils.toFXImage(ImageIO.read(url), null)
    }
}