package wtf.amari.hub.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import wtf.amari.hub.Hub
import wtf.amari.hub.managers.events.JoinManager

/**
 * Listener for handling player join events.
 */
class JoinListener : Listener {
    private val joinManager = JoinManager(Hub.instance)

    /**
     * Handles the PlayerJoinEvent.
     *
     * @param event The PlayerJoinEvent.
     */
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        event.joinMessage = null

        // Handle player join with JoinManager
        try {
            joinManager.handlePlayerJoin(event.player)
        } catch (e: Exception) {
            event.player.kickPlayer("An error occurred while joining the server. Please try again later.")
            e.printStackTrace()
        }
    }
}
