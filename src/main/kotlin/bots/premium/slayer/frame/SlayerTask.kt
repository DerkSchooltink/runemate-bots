package bots.premium.slayer.frame

import bots.premium.slayer.data.Monster
import bots.premium.slayer.settings.SlayerSettings
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.runemate.game.api.hybrid.local.Varps
import com.runemate.game.api.rs3.net.Bestiary
import com.runemate.game.api.rs3.net.Bestiary.lookupById
import java.io.IOException
import java.net.URL
import java.util.Collections.emptyList

private const val SLAYER_CATEGORY_BEASTS_ADDRESS = "http://services.runescape.com/m=itemdb_rs/bestiary/slayerBeasts.json?identifier="

class SlayerTask private constructor(val monster: Monster?, val amount: Int, val names: List<String>, val deathAnimations: List<Int>) {

    var requirements: Requirements? = monster?.requirements

    companion object {
        val currentSlayerTask: SlayerTask?
            get() {
                val beasts = lookupBySlayerCategory(Varps.getAt(185).value)
                val distinctNames = beasts.map { it.name }.distinct()
                val deathAnimations = beasts.distinct().map { it.animations.rights }.flatten()
                val monster = Monster.values().find { distinctNames.contains(it.monsterName) }
                val amount = Varps.getAt(183)?.value ?: 0

                return SlayerTask(monster, amount, distinctNames, deathAnimations)
            }

        fun updateTask(botSettings: SlayerSettings) {
            botSettings.task = currentSlayerTask
        }

        private fun lookupBySlayerCategory(slayerCategoryId: Int) = try {
            val response = URL(SLAYER_CATEGORY_BEASTS_ADDRESS + slayerCategoryId).openStream().let { stream ->
                String(stream.readBytes()).also { stream.close() }
            }

            JsonParser().parse(response).asJsonArray
                    .asSequence()
                    .filter { it.isJsonObject }
                    .map { it.asJsonObject.get("value").asInt }
                    .map { lookupById(it) }
                    .filterNotNull()
                    .toList()
        } catch (e: IOException) {
            emptyList<Bestiary.Beast>()
        } catch (e: JsonSyntaxException) {
            emptyList<Bestiary.Beast>()
        } catch (e: IllegalStateException) {
            emptyList<Bestiary.Beast>()
        }
    }
}
