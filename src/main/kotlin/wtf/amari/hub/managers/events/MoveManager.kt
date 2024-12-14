package wtf.amari.hub.managers.events

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.player.PlayerMoveEvent
import wtf.amari.hub.Hub

/**
 * Manages player movement operations.
 *
 * @param plugin The instance of the Hub plugin.
 */
class MoveManager(private val plugin: Hub) {

    /**
     * Handles player movement logic.
     *
     * @param event The PlayerMoveEvent.
     * @throws IllegalArgumentException if the configuration is invalid.
     */
    fun handlePlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val settings = Hub.settingsConfig

        // Validate the configuration for double jump settings
        validateConfig(settings, "settings.double-jump")

        // Check if double jump is enabled in the settings
        if (settings.getBoolean("settings.double-jump") && player.isOnGround) {
            player.allowFlight = true
            player.isFlying = false
        } else if (!settings.getBoolean("settings.double-jump")) {
            player.allowFlight = false
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