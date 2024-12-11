package wtf.amari.hub.managers

import org.bukkit.entity.Player
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

/**
 * Manages the configuration operations for the plugin.
 */
object ConfigManager {

    /**
     * Reloads the plugin's configuration file.
     *
     * @param executor The player executing the command.
     * @throws Exception if the configuration reload fails.
     */
    fun reloadConfig(executor: Player) {
        try {
            Hub.instance.reloadConfig()
            executor.sendMessage("&aConfig reloaded.".mm())
        } catch (e: Exception) {
            executor.sendMessage("&cFailed to reload config: ${e.message}".mm())
            throw e
        }
    }
}