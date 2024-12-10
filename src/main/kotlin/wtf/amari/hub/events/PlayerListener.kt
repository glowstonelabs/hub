package wtf.amari.hub.events

import kotlinx.coroutines.*
import me.tech.mcchestui.utils.openGUI
import org.bukkit.Bukkit.broadcast
import org.bukkit.Bukkit.getScheduler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import wtf.amari.hub.Hub
import wtf.amari.hub.guis.serverSelectorGUI
import wtf.amari.hub.managers.ItemGiverManager
import wtf.amari.hub.utils.mm

/**
 * Listener for player-related events.
 */
class PlayerListener : Listener {

    private val scope = CoroutineScope(Dispatchers.Default + Job())

    /**
     * Handles player join events.
     * @param event The player join event.
     */
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val scheduler = getScheduler()
        val instance = Hub.instance
        val config = instance.config
        val itemGiverManager = ItemGiverManager(Hub.instance)

        // Give server selector
        itemGiverManager.giveServerSelector(player)

        // Send join message
        config.getString("join-messages.join")?.let {
            event.joinMessage(it.replace("%player%", player.name).mm())
        }

        // Handle first-time player join
        if (!player.hasPlayedBefore()) {
            scheduler.runTaskLater(instance, Runnable {
                config.getString("join-messages.firstjoin")?.let {
                    broadcast("".mm())
                    broadcast(it.replace("%player%", player.name).mm())
                }
            }, 20L)
        }

        // Send welcome messages
        scheduler.runTaskLater(instance, Runnable {
            config.getStringList("join-messages.welcome-messages").forEach { message ->
                player.sendMessage(message.mm())
            }
        }, 20L)
    }

    /**
     * Handles player quit events.
     * @param event The player quit event.
     */
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val config = Hub.instance.config
        val quitMessage = config.getString("join-messages.quit")
        event.quitMessage(
            if (quitMessage.isNullOrEmpty()) null else quitMessage.replace("%player%", event.player.name).mm()
        )
    }

    /**
     * Handles player drop item events.
     * @param event The player drop item event.
     */
    @EventHandler
    fun onPlayerDrop(event: PlayerDropItemEvent) {
        val config = Hub.instance.config
        if (!config.getBoolean("settings.drop-items")) {
            config.getString("items.drop-message")?.let { event.player.sendMessage(it.mm()) }
            event.isCancelled = true
        }
    }

    /**
     * Handles player interact events.
     * @param event The player interact event.
     */
    @EventHandler
    fun onRightClick(event: PlayerInteractEvent) {
        val config = Hub.instance.config
        val player = event.player
        val item = player.inventory.itemInMainHand
        if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) && item.type.name == "COMPASS") {
            player.openGUI(serverSelectorGUI())
        }
    }

    /**
     * Clean up resources when the listener is no longer needed.
     */
    fun cleanup() {
        scope.cancel()
    }
}