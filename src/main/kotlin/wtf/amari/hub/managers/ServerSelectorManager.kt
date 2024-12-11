package wtf.amari.hub.managers

import me.tech.mcchestui.GUI
import me.tech.mcchestui.GUIType
import me.tech.mcchestui.item.item
import me.tech.mcchestui.utils.gui
import org.bukkit.Material
import org.bukkit.entity.Player
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

/**
 * Manager for creating the server selector GUI.
 */
class ServerSelectorManager {

    /**
     * Creates the server selector GUI.
     * This function constructs a GUI for selecting servers, using configuration values.
     * It includes null checks and error handling to ensure stability.
     *
     * @return The server selector GUI.
     * @throws IllegalArgumentException if required configuration values are missing or invalid.
     */
    fun createServerSelectorGUI(): GUI {
        val config = Hub.serverSelectorConfig

        // Validate configuration
        val title = config.getString("menu.title")?.mm()
            ?: throw IllegalArgumentException("Missing required key: menu.title in serverSelector.yml")
        val rows = config.getInt("menu.rows")
        if (rows <= 0) throw IllegalArgumentException("Missing or invalid required key: menu.rows in serverSelector.yml")

        return gui(
            plugin = Hub.instance,
            title = title,
            type = GUIType.Chest(rows)
        ) {
            all {
                item = item(Material.BLACK_STAINED_GLASS_PANE) { name = "".mm() }
            }

            // Iterate through server slots
            for (i in 1..3) { // Adjust the range as needed for the number of servers
                val slot = config.getInt("server-$i.slot")
                val materialName = config.getString("server-$i.material")
                val name = config.getString("server-$i.name")
                val lore = config.getStringList("server-$i.lore")
                val command = config.getString("server-$i.command")
                val message = config.getString("server-$i.message")

                if (materialName != null && name != null && lore != null) {
                    val material = Material.matchMaterial(materialName)
                    if (material != null) {
                        slot(slot, 1) {
                            item = item(material) {
                                this.name = name.mm()
                                this.lore = lore.map { it.mm() }
                                glowing = true
                                onClick {
                                    handleItemClick(it, command, message)
                                }
                            }
                        }
                    } else {
                        // Log error for invalid material
                        Hub.instance.logger.warning("Invalid material: $materialName in server selector configuration.")
                    }
                } else {
                    // Log error for missing configuration values
                    if (materialName == null) Hub.instance.logger.warning("Missing required key: server-$i.material in serverSelector.yml")
                    if (name == null) Hub.instance.logger.warning("Missing required key: server-$i.name in serverSelector.yml")
                    if (lore == null) Hub.instance.logger.warning("Missing required key: server-$i.lore in serverSelector.yml")
                }
            }
        }
    }

    /**
     * Handles the item click event in the server selector GUI.
     * Executes the command and sends a message to the player.
     *
     * @param player The player who clicked the item.
     * @param command The command to execute.
     * @param message The message to send to the player.
     */
    private fun handleItemClick(player: Player, command: String?, message: String?) {
        if (command != null) {
            player.performCommand(command)
        }
        if (message != null) {
            player.sendMessage(message.mm())
        }
        player.closeInventory()
    }
}