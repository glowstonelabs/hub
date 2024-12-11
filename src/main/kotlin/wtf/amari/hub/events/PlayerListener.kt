package wtf.amari.hub.events

import me.tech.mcchestui.utils.openGUI
import org.bukkit.Bukkit.broadcast
import org.bukkit.Bukkit.getScheduler
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import wtf.amari.hub.Hub
import wtf.amari.hub.managers.ItemGiverManager
import wtf.amari.hub.managers.ServerSelectorManager
import wtf.amari.hub.managers.SpawnManager
import wtf.amari.hub.utils.mm

/**
 * Listener for player-related events.
 */
class PlayerListener : Listener {

    private val itemGiverManager = ItemGiverManager(Hub.instance)
    private val serverSelectorManager = ServerSelectorManager()
    private val spawnManager = SpawnManager()

    /**
     * Handles player join events.
     * @param event The player join event.
     */
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val scheduler = getScheduler()
        val instance = Hub.instance
        val config = Hub.langConfig
        val lobby = spawnManager.getSpawn()

        // Validate configuration
        validateConfig(config, "join-messages.join")
        validateConfig(config, "join-messages.firstjoin")
        validateConfig(config, "join-messages.welcome-messages")

        // Force the player to the lobby spawn location
        if (lobby != null) {
            player.teleport(lobby)
        } else {
            player.sendMessage("&cSpawn location not set.".mm())
        }

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
        val config = Hub.langConfig
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
        val settings = Hub.settingsConfig
        val messages = Hub.langConfig
        validateConfig(settings, "settings.drop-items")
        validateConfig(messages, "items.drop-message")
        if (!settings.getBoolean("settings.drop-items")) {
            messages.getString("items.drop-message")?.let { event.player.sendMessage(it.mm()) }
            event.isCancelled = true
        }
    }

    /**
     * Handles player interact events.
     * @param event The player interact event.
     */
    @EventHandler
    fun onRightClick(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand
        if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) && item.type == Material.COMPASS) {
            player.openGUI(serverSelectorManager.createServerSelectorGUI())
        }
    }

    /**
     * Validates the configuration to ensure all required keys are present.
     *
     * @param config The configuration to validate.
     * @param key The key to check in the configuration.
     * @throws IllegalArgumentException if the key is missing.
     */
    private fun validateConfig(config: ConfigurationSection, key: String) {
        require(config.contains(key)) { "Missing required key: $key in ${config.name}.yml" }
    }
}