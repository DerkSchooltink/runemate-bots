package bots.free.summoning.settings

import bots.free.summoning.data.Obelisk

class BotSettings {
    lateinit var obelisk: Obelisk
    lateinit var ingredients: Array<String>
    lateinit var charm: String
    lateinit var pouchName: String

    var preset: Int = -1
}
