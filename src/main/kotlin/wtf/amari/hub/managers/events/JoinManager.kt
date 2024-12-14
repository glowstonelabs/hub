package wtf.amari.hub.managers.events

import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import wtf.amari.hub.Hub
import wtf.amari.hub.managers.ItemGiverManager
import wtf.amari.hub.managers.SpawnManager
import wtf.amari.hub.utils.mm

/**
 * Manages player join operations.
 *
 * @param plugin The instance of the Hub plugin.
 */
class JoinManager(private val plugin: Hub) {

    private val itemGiverManager = ItemGiverManager(plugin)
    private val spawnManager = SpawnManager()
    private val joinCooldowns = mutableMapOf<String, Long>()

    /**
     * Handles player join logic.
     *
     * @param player The player who joined.
     * @throws IllegalArgumentException if the configuration is invalid.
     */
    fun handlePlayerJoin(player: Player) {
        if (isPlayerOnCooldown(player)) {
            player.sendMessage("&cYou are on cooldown. Please wait before joining again.".mm())
            return
        }

        val scheduler = Bukkit.getScheduler()
        val config = Hub.langConfig
        val lobby = spawnManager.getSpawn()

        validateConfig(config, "join-messages.join")
        validateConfig(config, "join-messages.firstjoin")
        validateConfig(config, "join-messages.welcome-messages")

        // Teleport player to the lobby if set
        lobby?.let {
            player.teleport(it)
        } ?: run {
            player.sendMessage("&cSpawn location not set.".mm())
        }

        // Give server selector item to the player
        itemGiverManager.giveServerSelector(player)

        // Send join message
        config.getString("join-messages.join")?.let {
            Bukkit.broadcast(it.replace("%player%", player.name).mm())
        }

        // Handle first join message
        if (!player.hasPlayedBefore()) {
            scheduler.runTaskLater(plugin, Runnable {
                config.getString("join-messages.firstjoin")?.let {
                    Bukkit.broadcast(it.replace("%player%", player.name).mm())
                }
            }, 20L)
        }

        // Send welcome messages
        scheduler.runTaskLater(plugin, Runnable {
            config.getStringList("join-messages.welcome-messages").forEach { message ->
                player.sendMessage(message.mm())
            }
        }, 20L)

        // Add player to cooldown map
        joinCooldowns[player.name] = System.currentTimeMillis()
    }

    /**
     * Validates the configuration to ensure the required key exists.
     *
     * @param config The configuration section to validate.
     * @param key The key to check for existence.
     * @throws IllegalArgumentException if the key is missing in the configuration.
     */
    private fun validateConfig(config: ConfigurationSection, key: String) {
        require(config.contains(key)) { "Missing required key: $key in ${config.name}.yml" }
    }

    /**
     * Checks if the player is on cooldown.
     *
     * @param player The player to check.
     * @return True if the player is on cooldown, false otherwise.
     */
    private fun isPlayerOnCooldown(player: Player): Boolean {
        val cooldownTime = 2000L // 2 seconds cooldown
        val lastJoinTime = joinCooldowns[player.name] ?: return false
        return System.currentTimeMillis() - lastJoinTime < cooldownTime
    }
}