package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.hybrid.util.calculations.Random

class CombinePieces : ExtendedTask<CatacombsSlayer>() {

    private var topPiece: SpriteItem? = null
    private var middlePiece: SpriteItem? = null
    private var bottomPiece: SpriteItem? = null

    override fun validate(): Boolean {
        bottomPiece = Inventory.newQuery().names("Dark totem base").results().first()
        middlePiece = Inventory.newQuery().names("Dark totem middle").results().first()
        topPiece = Inventory.newQuery().names("Dark totem top").results().first()
        return bottomPiece != null && middlePiece != null && topPiece != null
    }

    override fun execute() {
        logger.info("Combining pieces...")
        if (Random.nextBoolean()) {
            if (Inventory.getSelectedItem() == null) bottomPiece?.interact("Use") else topPiece?.click()
        } else {
            if (Inventory.getSelectedItem() == null) topPiece?.interact("Use") else middlePiece?.click()
        }
    }
}
