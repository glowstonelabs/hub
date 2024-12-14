package wtf.amari.hub.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import wtf.amari.hub.Hub
import wtf.amari.hub.managers.events.QuitManager

/**
 * Listener for handling player quit events.
 */
class QuitListener : Listener {

    private val quitManager = QuitManager(Hub.instance)

    /**
     * Handles the PlayerQuitEvent.
     *
     * @param event The PlayerQuitEvent.
     */
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        try {
            quitManager.handlePlayerQuit(event)
        } catch (e: Exception) {
            event.quitMessage = null
            e.printStackTrace()
        }
    }
}