@file:Command(
    "config",
    description = "Reloads the configuration file.",
    usage = "Invalid usage. /config reload",
    permission = "hub.staff.config",
    permissionMessage = "You need hub.staff.config to do that!"
)

package wtf.amari.hub.commands.staff

import me.honkling.commando.common.annotations.Command
import org.bukkit.entity.Player
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

/**
 * Reloads the plugin's configuration file.
 *
 * @param executor The player executing the command.
 */
fun reload(executor: Player) {
    // Reload the plugin's configuration
    try {
        Hub.instance.reloadConfig()
        // Send a confirmation message
        executor.sendMessage("&aConfig reloaded.".mm())
    } catch (e: Exception) {
        // Handle any exceptions that occur during config reload
        executor.sendMessage("&cFailed to reload config: ${e.message}".mm())
    }
}