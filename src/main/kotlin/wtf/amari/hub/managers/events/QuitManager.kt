package wtf.amari.hub.managers.events

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.player.PlayerQuitEvent
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

/**
 * Manages player quit operations.
 *
 * @param plugin The instance of the Hub plugin.
 */
class QuitManager(
    private val plugin: Hub,
) {
    /**
     * Handles player quit logic.
     *
     * @param event The PlayerQuitEvent.
     * @throws IllegalArgumentException if the configuration is invalid.
     */
    fun handlePlayerQuit(event: PlayerQuitEvent) {
        val config = Hub.langConfig

        validateConfig(config, "join-messages.quit")

        val quitMessage = config.getString("join-messages.quit")
        event.quitMessage(
            if (quitMessage.isNullOrEmpty()) null else quitMessage.replace("%player%", event.player.name).mm(),
        )
    }

    /**
     * Validates the configuration to ensure the required key exists.
     *
     * @param config The configuration section to validate.
     * @param key The key to check for existence.
     * @throws IllegalArgumentException if the key is missing in the configuration.
     */
    private fun validateConfig(
        config: ConfigurationSection,
        key: String,
    ) {
        require(config.contains(key)) { "Missing required key: $key in ${config.name}.yml" }
    }
}
