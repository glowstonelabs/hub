package wtf.amari.hub.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import wtf.amari.hub.Hub
import wtf.amari.hub.managers.events.DropItemManager
import wtf.amari.hub.utils.mm

/**
 * Listener for handling player drop item events.
 */
class DropItemListener : Listener {
    private val dropItemManager = DropItemManager(Hub.instance)

    /**
     * Handles the PlayerDropItemEvent.
     *
     * @param event The PlayerDropItemEvent.
     */
    @EventHandler
    fun onPlayerDrop(event: PlayerDropItemEvent) {
        try {
            dropItemManager.handlePlayerDrop(event)
        } catch (e: Exception) {
            event.player.sendMessage("&cAn error occurred while dropping the item. Please try again later.".mm())
            e.printStackTrace()
        }
    }
}
