package bots.premium.woodcutting.settings

import bots.premium.woodcutting.data.BankingLocation
import bots.premium.woodcutting.data.ChopLocation
import bots.premium.woodcutting.data.Tree
import com.runemate.game.api.hybrid.location.Coordinate
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import java.util.regex.Pattern

data class WoodcuttingSettings(
    val loot: List<String> = listOf("Crystal geode", "Bird's nest", "Bird nest", "Clue nest (easy)", "Clue nest (medium)", "Clue nest (hard)", "Clue nest (elite)", "Anagogic ort"),
    val axePattern: Pattern = Pattern.compile(".*(axe|hatchet|Adze|Hatchet|Axe)"),
    val urnPattern: Pattern = Pattern.compile(".*woodcutting urn.*|.*urn enhancer.*", Pattern.CASE_INSENSITIVE),
    val jujuPattern: Pattern = Pattern.compile("juju woodcutting", Pattern.CASE_INSENSITIVE),
    val vipAreaCoordinates: List<Coordinate> = listOf(Coordinate(3180, 2747, 0), Coordinate(3180, 2753, 0), Coordinate(3192, 2753, 0), Coordinate(3192, 2747, 0)),
    val maximumDistanceToTree: Int = 25,
    val radius: Double = Double.MAX_VALUE,

    val bankingLocation: Property<BankingLocation?> = SimpleObjectProperty(),
    val chopLocation: Property<ChopLocation?> = SimpleObjectProperty(),
    val tree: Property<Tree> = SimpleObjectProperty(),
    val isPowerchopping: Property<Boolean> = SimpleObjectProperty(false),
    val isLootingItems: Property<Boolean> = SimpleObjectProperty(false),
    val isUsingJujuPotion: Property<Boolean> = SimpleObjectProperty(false),
    val isBurningLogs: Property<Boolean> = SimpleObjectProperty(false),
    val isUsingClosestBank: Property<Boolean> = SimpleObjectProperty(false)
)