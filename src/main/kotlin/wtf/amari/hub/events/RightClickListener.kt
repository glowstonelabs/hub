package wtf.amari.hub.events

import me.tech.mcchestui.GUI
import me.tech.mcchestui.utils.openGUI
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import wtf.amari.hub.managers.ServerSelectorManager
import wtf.amari.hub.utils.mm
import java.util.concurrent.ConcurrentHashMap

class RightClickListener : Listener {

    private val serverSelectorManager = ServerSelectorManager()
    private val rightClickCooldowns = ConcurrentHashMap<String, Long>()
    private var serverSelectorGUI: GUI? = null

    init {
        serverSelectorGUI = serverSelectorManager.createServerSelectorGUI()
    }

    @EventHandler
    fun onRightClick(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) && item.type == Material.COMPASS) {
            if (isPlayerOnCooldown(player)) {
                player.sendMessage("&cYou must wait before using this again.".mm())
                return
            }

            try {
                if (serverSelectorGUI == null) {
                    serverSelectorGUI = serverSelectorManager.createServerSelectorGUI()
                }
                player.openGUI(serverSelectorGUI!!)
                rightClickCooldowns[player.name] = System.currentTimeMillis()
            } catch (e: IllegalStateException) {
                serverSelectorGUI = serverSelectorManager.createServerSelectorGUI()
                player.openGUI(serverSelectorGUI!!)
                rightClickCooldowns[player.name] = System.currentTimeMillis()
            } catch (e: Exception) {
                player.sendMessage("&cAn error occurred while opening the server selector. Please try again later.".mm())
                e.printStackTrace()
            }
        }
    }

    private fun isPlayerOnCooldown(player: org.bukkit.entity.Player): Boolean {
        val cooldownTime = 2000L
        val lastClickTime = rightClickCooldowns[player.name] ?: return false
        return System.currentTimeMillis() - lastClickTime < cooldownTime
    }
}