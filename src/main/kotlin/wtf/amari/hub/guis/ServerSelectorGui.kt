package wtf.amari.hub.guis

import me.tech.mcchestui.GUI
import me.tech.mcchestui.GUIType
import me.tech.mcchestui.item.item
import me.tech.mcchestui.utils.gui
import net.kyori.adventure.text.event.ClickEvent
import org.bukkit.Material
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm


private fun createServerSelectorGUI(): GUI {
    return gui(
        plugin = Hub.instance,
        title = "<#36393F>&lDiscord".mm(),
        type = GUIType.Chest(rows = 3)
    ) {
        all {
            item = item(Material.BLACK_STAINED_GLASS_PANE) { name = " ".mm() }
        }

        slot(14, 1) {
            item = item(Material.BELL) {
                name = "<#7289DA>&lDiscord".mm()
                lore = listOf(
                    " ".mm(),
                    "<#F5F5F5>Connect with our community on <#7289DA>Discord<#F5F5F5>!".mm(),
                    " ".mm(),
                    "<#B3B3B3>Get updates, share your adventures,".mm(),
                    "<#B3B3B3>and chat with fellow players.".mm(),
                    " ".mm(),
                    "<#7289DA>Click here to join our discord!".mm(),
                )
                glowing = true
                onClick {
                    it.sendMessage(
                        "<#7289DA> Join the discord: <#F5F5F5>https://discord.zelamc.net".mm()
                            .clickEvent(ClickEvent.openUrl("https://discord.zelamc.net"))
                    )
                    it.closeInventory()
                }
            }
        }
    }
}