package bots.premium.slayer.ui

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.Monster
import bots.premium.slayer.data.SlayerMaster
import bots.premium.slayer.frame.SlayerTask
import bots.premium.slayer.tasks.*
import bots.premium.slayer.settings.SlayerSettings
import bots.premium.slayer.tasks.custom.DesertLizard
import bots.premium.slayer.tasks.custom.Gargoyle
import bots.premium.slayer.tasks.custom.Mogre
import bots.premium.slayer.tasks.custom.Strykewyrm
import quantum.api.framework.extensions.setSelection
import quantum.api.game.data.Antifire
import quantum.api.game.data.PrayerPotion
import com.runemate.game.api.hybrid.util.io.ManagedProperties
import com.runemate.game.api.script.framework.task.Task
import javafx.beans.property.SimpleListProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class SlayerUI(private val bot: Slayer) : StackPane(), Initializable {

    private val managedProperties: ManagedProperties = bot.settings
    private val slayerSettings: SlayerSettings = bot.slayerSettings
    private val items = slayerSettings.lootItems

    @FXML
    private lateinit var slayerMasterComboBox: ComboBox<SlayerMaster>

    @FXML
    private lateinit var npcContactCheckBox: CheckBox

    @FXML
    private lateinit var skipComboBox: ComboBox<Monster>

    @FXML
    private lateinit var addSkipButton: Button

    @FXML
    private lateinit var skipListView: ListView<Monster>

    @FXML
    private lateinit var deleteSkipButton: Button

    @FXML
    private lateinit var addLootButton: Button

    @FXML
    private lateinit var lootTextField: TextField

    @FXML
    private lateinit var itemLootListView: ListView<String>

    @FXML
    private lateinit var deleteLootItemButton: Button

    @FXML
    private lateinit var importUnsupportedTasksButton: Button

    @FXML
    private lateinit var lootStackablesCheckBox: CheckBox

    @FXML
    private lateinit var usePresetLootListCheckBox: CheckBox

    @FXML
    private lateinit var antifireComboBox: ComboBox<Antifire>

    @FXML
    private lateinit var useQuickPrayerCheckBox: CheckBox

    @FXML
    private lateinit var prayerPotionComboBox: ComboBox<PrayerPotion>

    @FXML
    private lateinit var startButton: Button

    private val listProperty = SimpleListProperty<Monster>(slayerSettings.skipTasks)

    private fun startButtonAction() = EventHandler<ActionEvent> {
        managedProperties.setProperty("slayer_master", slayerMasterComboBox.selectionModel.selectedIndex.toString())
        managedProperties.setProperty("use_npc_contact", npcContactCheckBox.isSelected.toString())
        managedProperties.setProperty("loot_stackables", lootStackablesCheckBox.isSelected.toString())
        managedProperties.setProperty("use_loot_list", usePresetLootListCheckBox.isSelected.toString())
        managedProperties.setProperty("use_quickprayer", useQuickPrayerCheckBox.isSelected.toString())
        managedProperties.setProperty("antifire", antifireComboBox.selectionModel.selectedIndex.toString())
        managedProperties.setProperty("prayer_potion", prayerPotionComboBox.selectionModel.selectedIndex.toString())

        startButton.text = "Update"
        bot.platform.invokeAndWait {
            bot.tasks.clear()
            bot.add(*setSettings())
            slayerSettings.task = SlayerTask.currentSlayerTask
        }
    }

    private fun addCustomItemButtonAction() = EventHandler<ActionEvent> {
        if (!items.contains(lootTextField.text)) {
            items.add(lootTextField.text)
            itemLootListView.items.setAll(items.sorted())
        }
    }

    private fun addSkipTaskButtonAction() = EventHandler<ActionEvent> {
        if (!slayerSettings.skipTasks.contains(skipComboBox.selectionModel.selectedItem)) {
            slayerSettings.skipTasks.add(skipComboBox.selectionModel.selectedItem)
            skipListView.items = slayerSettings.skipTasks.sorted()
        }
    }

    private fun removeButtonAction() = EventHandler<ActionEvent> {
        if (items.size > 0) items.remove(itemLootListView.selectionModel.selectedItem)
    }

    private fun removeSkipTaskButtonAction() = EventHandler<ActionEvent> {
        if (slayerSettings.skipTasks.size > 0) slayerSettings.skipTasks.remove(skipListView.selectionModel.selectedItem)
    }

    private val specialCombatTasks = arrayOf(Gargoyle(), Strykewyrm(), DesertLizard(), Mogre())

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //slayer
        slayerMasterComboBox.items.setAll(*SlayerMaster.values())
        slayerMasterComboBox.setSelection(managedProperties.getProperty("slayer_master"))
        npcContactCheckBox.setSelection(managedProperties.getProperty("use_npc_contact"))
        skipListView.itemsProperty().bind(listProperty)

        //antifire combobox
        antifireComboBox.items.setAll(*Antifire.values())
        antifireComboBox.setSelection(managedProperties.getProperty("antifire"))

        //prayer
        prayerPotionComboBox.items.setAll(*PrayerPotion.values())
        prayerPotionComboBox.setSelection(managedProperties.getProperty("prayer_potion"))
        useQuickPrayerCheckBox.setSelection(managedProperties.getProperty("use_quickprayer"))

        //looting
        addLootButton.onAction = addCustomItemButtonAction()
        deleteLootItemButton.onAction = removeButtonAction()
        lootStackablesCheckBox.setSelection(managedProperties.getProperty("loot_stackables"))
        usePresetLootListCheckBox.setSelection(managedProperties.getProperty("use_loot_list"))

        //skipping task
        skipComboBox.items.setAll(*Monster.values())
        addSkipButton.onAction = addSkipTaskButtonAction()
        deleteSkipButton.onAction = removeSkipTaskButtonAction()
        importUnsupportedTasksButton.onAction = importUnsupportedTasksButtonAction()

        //startbutton
        startButton.onAction = startButtonAction()
    }

    private fun importUnsupportedTasksButtonAction() = EventHandler<ActionEvent> {
        slayerSettings.skipTasks.setAll(Monster.unsupportedMonsters)
    }

    private fun setSettings(): Array<Task> {
        val list = mutableSetOf<Task>()

        slayerSettings.lootPriceMinimum = 5000
        slayerSettings.lootItemList.clear()

        if (lootStackablesCheckBox.isSelected) setLootStackables()

        if (usePresetLootListCheckBox.isSelected) setUsingStandardLootList()

        if (npcContactCheckBox.isSelected) {
            setUsingNPCContact()
            list.add(GetSlayerTaskWithLunar())
        }

        if (useQuickPrayerCheckBox.isSelected) {
            setUsingQuickPrayer()
            setPrayerPotion()
            Collections.addAll(list, ActivatePrayer(), DeactivatePrayer(), DrinkPrayerPotion())
        }

        if (skipListView.items.size > 0) {
            setSkipTasks()
            list.add(SkipTask())
        }

        setSlayerMaster()
        setAntifirePotion()

        Collections.addAll(list, WalkToNearestBank(), WithdrawEquipment(), TraverseToMonster(), Attack(), Loot(), Eat(), EnableAutoRetaliate(), TraverseToMaster(), GetTask(), EmergencyTeleport(), DrinkAntifire(), UnsupportedTaskHandler(), GetRequirements())
        Collections.addAll(list, *specialCombatTasks)

        return list.toTypedArray()
    }

    private fun setSlayerMaster() {
        slayerSettings.slayerMaster = slayerMasterComboBox.value
    }

    private fun setLootStackables() {
        slayerSettings.lootStackables = true
    }

    private fun setAntifirePotion() {
        slayerSettings.antifirePotion = antifireComboBox.value
    }

    private fun setUsingNPCContact() {
        slayerSettings.usingNPCContact = true
    }

    private fun setUsingStandardLootList() {
        slayerSettings.usingStandardLootList = usePresetLootListCheckBox.isSelected
    }

    private fun setUsingQuickPrayer() {
        slayerSettings.useQuickPrayer = useQuickPrayerCheckBox.isSelected
    }

    private fun setSkipTasks() {
        slayerSettings.skipTasks.addAll(ArrayList(skipListView.items))
    }

    private fun setPrayerPotion() {
        slayerSettings.prayerPotion = prayerPotionComboBox.selectionModel.selectedItem
    }
}
