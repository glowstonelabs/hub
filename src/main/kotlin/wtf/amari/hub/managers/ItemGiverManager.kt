package wtf.amari.hub.managers

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

/**
 * Manager for giving items to players.
 * @param plugin The plugin instance.
 */
class ItemGiverManager(private val plugin: Plugin) {

    private val serverSelector: ItemStack by lazy { createServerSelector() }

    init {
        validateConfig()
    }

    /**
     * Gives the server selector item to the target player.
     * @param target The target player.
     */
    fun giveServerSelector(target: Player) {
        target.inventory.setItem(4, serverSelector.clone()) // Slot 1 in Minecraft is index 0
    }

    /**
     * Creates the server selector item.
     * @return The server selector item.
     */
    private fun createServerSelector(): ItemStack {
        val serverSelector = ItemStack(Material.COMPASS)

        val config = Hub.serverSelectorConfig
        val meta = serverSelector.itemMeta ?: return serverSelector

        meta.displayName(config.getString("serverSelector.name")?.mm())
        meta.lore(config.getStringList("serverSelector.lore").map { it.mm() })
        meta.addEnchant(org.bukkit.enchantments.Enchantment.FLAME, 1, true)
        meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS)
        meta.isUnbreakable = true
        serverSelector.itemMeta = meta

        return serverSelector
    }

    /**
     * Validates the configuration to ensure all required sections and values are present.
     *
     * @throws IllegalArgumentException if the configuration is invalid.
     */
    private fun validateConfig() {
        val config = Hub.serverSelectorConfig
        require(config.contains("serverSelector.name")) { "Missing required key: serverSelector.name in serverSelector.yml" }
        require(config.contains("serverSelector.lore")) { "Missing required key: serverSelector.lore in serverSelector.yml" }
    }
}