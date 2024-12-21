@file:Command(
    "config",
    description = "Reloads the configuration file.",
    usage = "Invalid usage. /config reload",
    permission = "hub.staff.config",
    permissionMessage = "You need hub.staff.config to do that!",
)

package wtf.amari.hub.commands.staff

import me.honkling.commando.common.annotations.Command
import org.bukkit.entity.Player
import wtf.amari.hub.managers.ConfigManager
import wtf.amari.hub.utils.mm

/**
 * Executes the reload command.
 *
 * @param executor The player executing the command.
 * @throws IllegalArgumentException if the executor is not a player.
 */
fun reload(executor: Player) {
    try {
        ConfigManager.reloadConfigs(executor)
        executor.sendMessage("&aConfiguration reloaded successfully.".mm())
    } catch (e: Exception) {
        executor.sendMessage("Failed to reload configuration: ${e.message}")
        e.printStackTrace()
    }
}
