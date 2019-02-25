package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import quantum.api.core.ExtendedTask
import quantum.api.framework.playersense.QuantumPlayerSense
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.hybrid.util.calculations.Random
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar

class Eat : ExtendedTask<Slayer>() {

    private var food: SpriteItem? = null

    override fun validate(): Boolean {
        food = Inventory.newQuery().actions("Eat").results().random()
        return Health.getCurrentPercent() < 50 && food != null
    }

    override fun execute() {
        if (Random.nextBoolean() && !ActionBar.newQuery().names("Guthix's Blessing").results().isEmpty()) {
            if (ActionBar.newQuery().activatable(true).names("Guthix's Blessing").results().first()?.activate() == true) {
                logger.info("Using Guthix's Blessing...")
            }
        } else {
            (0 until QuantumPlayerSense.SPAM_CLICK_COUNT.asInteger()).run {
                logger.info("Eating...")
                food?.interact("Eat")
            }
        }
    }
}
