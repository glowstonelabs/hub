package wtf.amari.hub.managers

import me.tech.mcchestui.GUI
import me.tech.mcchestui.GUIType
import me.tech.mcchestui.item.GUIItem
import me.tech.mcchestui.item.item
import me.tech.mcchestui.utils.gui
import org.bukkit.Material
import org.bukkit.entity.Player
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

class ServerSelectorManager {
    private val cachedItems = mutableMapOf<String, GUIItem>()

    fun createServerSelectorGUI(): GUI {
        val config = Hub.serverSelectorConfig

        val title =
            config.getString("menu.title")?.mm()
                ?: throw IllegalArgumentException("Missing required key: menu.title in serverSelector.yml")
        val rows = config.getInt("menu.rows")
        if (rows <= 0) throw IllegalArgumentException("Missing or invalid required key: menu.rows in serverSelector.yml")

        val gui =
            gui(
                plugin = Hub.instance,
                title = title,
                type = GUIType.Chest(rows),
            ) {
                all {
                    item = item(Material.BLACK_STAINED_GLASS_PANE) { name = "".mm() }
                }
                onDragItem = { _, _ -> false }
                allowHotBarSwap = false
                allowItemPickup = false
                allowShiftClick = false
            }

        for (i in 1..3) {
            val slot = config.getInt("server-$i.slot")
            val materialName = config.getString("server-$i.material")
            val name = config.getString("server-$i.name")
            val lore = config.getStringList("server-$i.lore")
            val command = config.getString("server-$i.command")
            val message = config.getString("server-$i.message")

            if (materialName != null && name != null && lore != null) {
                val material = Material.matchMaterial(materialName)
                if (material != null) {
                    gui.slot(slot, 1) {
                        item =
                            getCachedItem("server-$i") {
                                item(material) {
                                    this.name = name.mm()
                                    this.lore = lore.map { it.mm() }
                                    glowing = true
                                }
                            }
                        onClick {
                            handleItemClick(it, command, message)
                        }
                    }
                } else {
                    Hub.instance.logger.warning("Invalid material: $materialName in server selector configuration.")
                }
            } else {
                if (materialName == null) Hub.instance.logger.warning("Missing required key: server-$i.material in serverSelector.yml")
                if (name == null) Hub.instance.logger.warning("Missing required key: server-$i.name in serverSelector.yml")
                if (lore == null) Hub.instance.logger.warning("Missing required key: server-$i.lore in serverSelector.yml")
            }
        }

        return gui
    }

    private fun getCachedItem(
        key: String,
        itemSupplier: () -> GUIItem,
    ): GUIItem = cachedItems.getOrPut(key, itemSupplier)

    private fun handleItemClick(
        player: Player,
        command: String?,
        message: String?,
    ) {
        if (command != null) {
            player.performCommand(command)
        }
        if (message != null) {
            player.sendMessage(message.mm())
        }
        player.closeInventory()
    }
}
