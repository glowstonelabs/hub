package wtf.amari.hub.managers.events

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.player.PlayerDropItemEvent
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

/**
 * Manages player drop item operations.
 *
 * @param plugin The instance of the Hub plugin.
 */
class DropItemManager(private val plugin: Hub) {

    /**
     * Handles player drop item logic.
     *
     * @param event The PlayerDropItemEvent.
     * @throws IllegalArgumentException if the configuration is invalid.
     */
    fun handlePlayerDrop(event: PlayerDropItemEvent) {
        val settings = Hub.settingsConfig
        val messages = Hub.langConfig

        validateConfig(settings, "settings.drop-items")
        validateConfig(messages, "items.drop-message")

        if (!settings.getBoolean("settings.drop-items")) {
            messages.getString("items.drop-message")?.let { event.player.sendMessage(it.mm()) }
            event.isCancelled = true
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