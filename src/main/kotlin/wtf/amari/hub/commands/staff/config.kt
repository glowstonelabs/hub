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
import wtf.amari.hub.managers.ConfigManager

/**
 * Handles the /config command for reloading the plugin's configuration.
 */
object ConfigCommand {

    /**
     * Executes the reload command.
     *
     * @param executor The player executing the command.
     */
    fun reload(executor: Player) {
        ConfigManager.reloadConfig(executor)
    }
}