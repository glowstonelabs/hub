package wtf.amari.hub.managers

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

class ItemGiverManager(private val plugin: Plugin) {

    fun giveServerSelector(target: Player) {
        val serverSelector = createServerSelector()
        target.inventory.setItem(4, serverSelector) // Slot 1 in Minecraft is index 0
    }

    private fun createServerSelector(): ItemStack {
        val serverSelector = ItemStack(Material.COMPASS)

        val config = Hub.instance.config
        val meta = serverSelector.itemMeta ?: return serverSelector

        meta.displayName(
            config.getString("serverSelector.name")?.mm()
        )

        meta.lore(config.getStringList("serverSelector.lore").map {
            it.mm()
        })

        meta.addEnchant(org.bukkit.enchantments.Enchantment.EFFICIENCY, 1, true)
        meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS)
        meta.isUnbreakable = true
        serverSelector.itemMeta = meta

        return serverSelector
    }
}