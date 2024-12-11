@file:Command(
    "setlobby",
    "setspawn",
    description = "Sets the lobby spawn location",
    usage = "Invalid usage. /setlobby",
    permission = "hub.staff.setlobby",
    permissionMessage = "You need hub.staff.setlobby to do that!"
)

package wtf.amari.hub.commands.staff

import me.honkling.commando.common.annotations.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import wtf.amari.hub.managers.SpawnManager
import wtf.amari.hub.utils.mm

/**
 * Sets the lobby spawn location to the player's current location.
 *
 * @param sender The command sender.
 */
fun setlobby(sender: CommandSender) {
    // Check if the sender is a player
    if (sender !is Player) {
        sender.sendMessage("&cOnly players can use this command.".mm())
        return
    }

    // Set the spawn location using the SpawnManager
    val spawnManager = SpawnManager()
    spawnManager.setSpawn(sender.location)
    sender.sendMessage("&aLobby spawn location set to your current location.".mm())
}