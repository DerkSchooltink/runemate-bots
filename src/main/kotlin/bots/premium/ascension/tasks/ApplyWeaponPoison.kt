package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.script.Execution

class ApplyWeaponPoison : ExtendedTask<Ascension>() {

    private val isUsingWeaponPoison: Boolean = bot.ascensionSettings.isUsingWeaponPoison && bot.ascensionSettings.weaponPoison?.varbit?.value == 0

    override fun validate() = isUsingWeaponPoison
                && Inventory.containsAnyOf(bot.ascensionSettings.weaponPoison?.pattern)
                && !Bank.isOpen()
                && !Npcs.newQuery().names("Rorarius").results().isEmpty()

    override fun execute() {
        logger.info("Applying weapon poison...")
        val pot = Inventory.newQuery().names(bot.ascensionSettings.weaponPoison?.pattern).results().first()
        if (pot != null && pot.interact("Apply")) {
            Execution.delayUntil({ bot.ascensionSettings.weaponPoison?.varbit?.value != 0 }, 800, 1200)
        }
    }
}