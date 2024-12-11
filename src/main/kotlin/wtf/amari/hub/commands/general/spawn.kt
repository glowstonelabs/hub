@file:Command(
    "spawn",
    "lobby",
    description = "Goes to the lobby spawn location",
    usage = "Invalid usage. /spawn"
)

package wtf.amari.hub.commands.general

import me.honkling.commando.common.annotations.Command
import org.bukkit.entity.Player
import wtf.amari.hub.managers.SpawnManager
import wtf.amari.hub.utils.mm

/**
 * Teleports the player to the lobby spawn location.
 *
 * @param sender The player executing the command.
 */
fun spawn(sender: Player) {
    // Get the spawn location from the SpawnManager
    val spawnManager = SpawnManager()
    val spawn = spawnManager.getSpawn()

    // If the spawn location is not set, notify the player
    if (spawn == null) {
        sender.sendMessage("&cSpawn location not set.".mm())
        return
    }

    // Teleport the player to the spawn location
    sender.teleport(spawn)
    sender.sendMessage("&aTeleported to the lobby spawn location.".mm())
}