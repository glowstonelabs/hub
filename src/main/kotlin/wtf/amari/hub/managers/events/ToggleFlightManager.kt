package wtf.amari.hub.managers.events

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.player.PlayerToggleFlightEvent
import wtf.amari.hub.Hub

/**
 * Manages player flight toggle operations.
 *
 * @param plugin The instance of the Hub plugin.
 */
class ToggleFlightManager(private val plugin: Hub) {

    /**
     * Handles player flight toggle logic.
     *
     * @param event The PlayerToggleFlightEvent.
     * @throws IllegalArgumentException if the configuration is invalid.
     */
    fun handlePlayerToggleFlight(event: PlayerToggleFlightEvent) {
        val player = event.player
        val settings = Hub.settingsConfig

        // Validate the configuration for double jump settings
        validateConfig(settings, "settings.double-jump")

        // Check if double jump is enabled in the settings
        if (settings.getBoolean("settings.double-jump")) {
            event.isCancelled = true
            player.allowFlight = false
            player.isFlying = false
            player.velocity = player.location.direction.multiply(1.5).setY(1.0)
        }
    }

    /**
     * Validates the configuration to ensure the required key exists.
     *
     * @param config The configuration section to validate.
     * @param key The key to check for existence.
     * @throws IllegalArgumentException if the key is missing in the configuration.
     */
    private fun validateConfig(config: ConfigurationSection, key: String) {
        require(config.contains(key)) { "Missing required key: $key in ${config.name}.yml" }
    }
}