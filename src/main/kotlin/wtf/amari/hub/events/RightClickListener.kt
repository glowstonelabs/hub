package wtf.amari.hub.events

import me.tech.mcchestui.utils.openGUI
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import wtf.amari.hub.managers.ServerSelectorManager
import wtf.amari.hub.utils.mm

/**
 * Listener for handling player right-click events.
 */
class RightClickListener : Listener {

    private val serverSelectorManager = ServerSelectorManager()
    private val rightClickCooldowns = mutableMapOf<String, Long>()

    /**
     * Handles the PlayerInteractEvent.
     *
     * @param event The PlayerInteractEvent.
     */
    @EventHandler
    fun onRightClick(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        // Check if the action is a right-click with a compass
        if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) && item.type == Material.COMPASS) {
            // Check for cooldown
            if (isPlayerOnCooldown(player)) {
                player.sendMessage("&cYou must wait before using this again.".mm())
                return
            }

            // Open the server selector GUI
            try {
                player.openGUI(serverSelectorManager.createServerSelectorGUI())
                // Add player to cooldown map
                rightClickCooldowns[player.name] = System.currentTimeMillis()
            } catch (e: Exception) {
                player.sendMessage("&cAn error occurred while opening the server selector. Please try again later.".mm())
                e.printStackTrace()
            }
        }
    }

    /**
     * Checks if the player is on cooldown.
     *
     * @param player The player to check.
     * @return True if the player is on cooldown, false otherwise.
     */
    private fun isPlayerOnCooldown(player: org.bukkit.entity.Player): Boolean {
        val cooldownTime = 2000L // 2 seconds cooldown
        val lastClickTime = rightClickCooldowns[player.name] ?: return false
        return System.currentTimeMillis() - lastClickTime < cooldownTime
    }
}