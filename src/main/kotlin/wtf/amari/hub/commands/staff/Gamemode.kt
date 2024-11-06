@file:Command(
    "gamemode",
    description = "Changes Gamemode.",
    usage = "Invalid usage. /gamemode (adventure|creative|spectator|survival) [player]",
    permission = "hub.staff.gamemode",
    permissionMessage = "You need hub.staff.gamemode to do that!"
)

package wtf.amari.hub.commands.staff

import me.honkling.commando.common.annotations.Command
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import wtf.amari.hub.utils.mm

/**
 * Changes the executor's gamemode.
 *
 * @param executor The player executing the command.
 * @param mode The desired gamemode.
 */
fun gamemode(executor: Player, mode: String) {
    val gameMode = getGameMode(mode, executor) ?: return
    executor.gameMode = gameMode
    executor.sendMessage("&aGamemode set to &c${gameMode.name.lowercase()}&a!".mm())
}

/**
 * Changes the target player's gamemode or the executor's if no target is specified.
 *
 * @param executor The player executing the command.
 * @param mode The desired gamemode.
 * @param targetName The name of the target player.
 */
fun gamemode(executor: Player, mode: String, targetName: String?) {
    val target = targetName?.let { Bukkit.getPlayer(it) } ?: executor
    val gameMode = getGameMode(mode, executor) ?: return
    target.gameMode = gameMode
    executor.sendMessage("&aSet gamemode of &c${target.name} &ato &c${gameMode.name.lowercase()}&a!".mm())
}

/**
 * Retrieves the GameMode enum based on the provided mode string.
 *
 * @param mode The desired gamemode as a string.
 * @param executor The player executing the command.
 * @return The corresponding GameMode enum or null if invalid.
 */
private fun getGameMode(mode: String, executor: Player): GameMode? {
    return when (mode.lowercase()) {
        "adventure", "a" -> GameMode.ADVENTURE
        "creative", "c" -> GameMode.CREATIVE
        "spectator", "sp" -> GameMode.SPECTATOR
        "survival", "s" -> GameMode.SURVIVAL
        else -> {
            executor.sendMessage("&cInvalid gamemode.".mm())
            null
        }
    }
}