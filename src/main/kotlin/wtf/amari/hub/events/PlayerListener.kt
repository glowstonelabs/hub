package wtf.amari.hub.events

import org.bukkit.Bukkit.broadcast
import org.bukkit.Bukkit.getScheduler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

class PlayerListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val scheduler = getScheduler()
        val instance = Hub.instance
        val config = instance.config

        // Send join message
        config.getString("messages.join")?.let {
            event.joinMessage(it.replace("%player%", player.name).mm())
        }

        // Handle first-time player join
        if (!player.hasPlayedBefore()) {
            scheduler.runTaskLater(instance, Runnable {
                config.getString("messages.firstjoin")?.let {
                    broadcast("".mm())
                    broadcast(it.replace("%player%", player.name).mm())
                }
            }, 20L)
        }

        // Send welcome messages
        scheduler.runTaskLater(instance, Runnable {
            config.getStringList("messages.welcome-messages").forEach { message ->
                player.sendMessage(message.mm())
            }
        }, 20L)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val config = Hub.instance.config
        val quitMessage = config.getString("messages.quit")
        event.quitMessage(
            if (quitMessage.isNullOrEmpty()) null else quitMessage.replace("%player%", event.player.name).mm()
        )
    }

    @EventHandler
    fun onPlayerDrop(event: PlayerDropItemEvent) {
        val config = Hub.instance.config
        if (!config.getBoolean("settings.drop-items")) {
            config.getString("messages.item-drop-message")?.let { event.player.sendMessage(it.mm()) }
            event.isCancelled = true
        }
    }
}