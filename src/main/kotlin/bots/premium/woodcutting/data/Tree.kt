package bots.premium.woodcutting.data

import java.util.*

enum class Tree(private var treeName: String, var trees: Array<out String>) {
    TREE("Tree", arrayOf("Tree", "Dead tree", "Evergreen"), "Logs"),
    OAK("Oak tree", arrayOf("Oak", "Oak tree"), "Oak logs"),
    WILLOW("Willow tree", arrayOf("Willow", "Willow tree", "Willow Trees"), "Willow logs"),
    MAPLE("Maple tree", arrayOf("Maple Trees", "Maple tree", "Maple", "Maple Tree"), "Maple logs"),
    YEW("Yew tree", arrayOf("Yew tree", "Yew Trees", "Yew"), "Yew logs"),
    MAGIC("Magic tree", arrayOf("Magic Tree", "Magic Trees", "Magic tree"), "Magic logs"),
    CRYSTAL_TREE("Crystal tree", arrayOf("Crystal tree shard"), "Crystal tree shard"),
    IVY("Ivy", arrayOf("Ivy")),
    MAHOGANY("Mahogany tree", arrayOf("Mahogany", "Mahogany tree", "Mahogany Trees"), "Mahogany logs"),
    TEAK("Teak tree", arrayOf("Teak", "Teak tree", "Teak Trees"), "Teak logs"),
    BRANCHING_CRYSTAL("Branching crystal", arrayOf("Branching crystal")),
    ACADIA("Acadia tree", "Acadia logs", arrayOf("Acadia tree"), hashMapOf(intArrayOf(50, -275, 20) to intArrayOf(-75, -300, 0))),
    BAMBOO("Bamboo", arrayOf("Bamboo"), "Bamboo"),
    REDWOOD("Redwood", arrayOf("Redwood"), "Redwood logs"),
    ARCTIC_PINE("Arctic pine", arrayOf("Arctic pine"), "Arctic pine logs"),
    HOLLOW("Hollow Tree", arrayOf("Bark"), "Hollow Tree"),
    ACHEY("Achey Tree", arrayOf("Achey Tree"), "Achey tree logs"),
    ELDER("Elder tree", arrayOf("Elder tree"), "Elder logs");

    var logs: String? = ""
    var forcedModel: HashMap<IntArray, IntArray>? = null

    constructor(treeName: String, trees: Array<out String>, logs: String) : this(treeName, trees) {
        this.logs = logs
    }

    constructor(name: String, logs: String, trees: Array<out String>, forcedModel: HashMap<IntArray, IntArray>) : this(name, trees, logs) {
        this.forcedModel = forcedModel
    }

    override fun toString() = treeName
}
