package wtf.amari.hub.guis

import me.tech.mcchestui.GUI
import me.tech.mcchestui.GUIType
import me.tech.mcchestui.item.item
import me.tech.mcchestui.utils.gui
import org.bukkit.Material
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm


public fun serverSelectorGUI(): GUI {
    return gui(
        plugin = Hub.instance,
        title = "<#36393F>&lServerSelector".mm(),
        type = GUIType.Chest(rows = 3)
    ) {
        all {
            item = item(Material.BLACK_STAINED_GLASS_PANE) { name = "".mm() }
        }

        slot(11, 1) {
            item = item(Material.GRASS_BLOCK) {
                name = "<#7289DA>&lServerSelector".mm()
                lore = listOf(
                    " ".mm(),
                    "<#7289DA>Testing".mm(),
                )
                glowing = true
                onClick {
                    it.performCommand("server testing")
                    it.sendMessage("&aConnecting to Testing server...".mm())
                    it.closeInventory()
                }
            }
        }
    }
}