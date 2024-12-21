package wtf.amari.hub.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import wtf.amari.hub.Hub
import wtf.amari.hub.managers.events.MoveManager

/**
 * Listener for handling player movement events.
 */
class MoveListener : Listener {
    private val moveManager = MoveManager(Hub.instance)

    /**
     * Handles the PlayerMoveEvent.
     *
     * @param event The PlayerMoveEvent.
     */
    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        try {
            moveManager.handlePlayerMove(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
