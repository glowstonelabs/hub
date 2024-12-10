@file:Command(
    "clearchat",
    "chatclear",
    description = "Clears the chat",
    usage = "Invalid usage. /clearchat",
    permission = "hub.staff.clearchat",
    permissionMessage = "You need hub.staff.clearchat to do that!"
)

package wtf.amari.hub.commands.staff

import me.honkling.commando.common.annotations.Command
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import wtf.amari.hub.utils.mm

/**
 * Clears the chat for all players without the clear chat permission and sends a confirmation message.
 *
 * @param executor The player executing the command.
 */
fun clearChat(executor: Player) {
    // Validate user input
    if (executor !is Player) {
        executor.sendMessage("&cOnly players can execute this command.".mm())
        return
    }

    // Clear the chat by broadcasting 100 empty lines to players without the permission
    val playersToClear = Bukkit.getOnlinePlayers().filter { !it.hasPermission("hub.staff.clearchat") }
    repeat(100) {
        playersToClear.forEach { player -> player.sendMessage(" ".mm()) }
    }

    // Send a confirmation message to all players
    Bukkit.broadcast("&cChat has been cleared by &e${executor.name}".mm())
}