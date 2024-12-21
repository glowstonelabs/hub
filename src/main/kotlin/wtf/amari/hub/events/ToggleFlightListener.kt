package wtf.amari.hub.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleFlightEvent
import wtf.amari.hub.Hub
import wtf.amari.hub.managers.events.ToggleFlightManager

/**
 * Listener for handling player flight toggle events.
 */
class ToggleFlightListener : Listener {
    private val toggleFlightManager = ToggleFlightManager(Hub.instance)

    /**
     * Handles the PlayerToggleFlightEvent.
     *
     * @param event The PlayerToggleFlightEvent.
     */
    @EventHandler
    fun onPlayerToggleFlight(event: PlayerToggleFlightEvent) {
        try {
            toggleFlightManager.handlePlayerToggleFlight(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
