package quantum.api.ui.data

import quantum.api.ui.calculations.Calculations
import com.runemate.game.api.hybrid.local.Skill
import com.runemate.game.api.hybrid.util.StopWatch
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

data class Skill(
        var name: String,
        var skill: Skill,
        var level: Int,
        var experienceGained: Int,
        var experienceToNextLevel: Int
) {
    inner class Properties(private var timer: StopWatch) {
        var currentLevelProperty = SimpleIntegerProperty(0)
        var totalExperienceGainedProperty = SimpleIntegerProperty(0)
        var hourlyExperienceGainedProperty = SimpleIntegerProperty(0)
        var timeToNextLevelProperty = SimpleStringProperty("Unknown")

        fun setProperties() {
            Platform.runLater {
                currentLevelProperty.set(level)
                totalExperienceGainedProperty.set(experienceGained)
                hourlyExperienceGainedProperty.set(Calculations.hourlyRate(experienceGained, timer))
                timeToNextLevelProperty.set(Calculations.timeToLevel(experienceGained.toLong(), experienceToNextLevel.toLong(), timer))
            }
        }
    }
}