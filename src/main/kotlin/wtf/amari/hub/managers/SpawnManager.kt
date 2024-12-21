package wtf.amari.hub.managers

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration
import wtf.amari.hub.Hub

/**
 * Manages the spawn location operations for the plugin.
 */
class SpawnManager {
    private val config: FileConfiguration = Hub.settingsConfig

    /**
     * Sets the spawn location in the configuration.
     *
     * @param location The location to set as the spawn.
     */
    fun setSpawn(location: Location) {
        config.set("settings.spawn.world", location.world?.name)
        config.set("settings.spawn.x", location.x)
        config.set("settings.spawn.y", location.y)
        config.set("settings.spawn.z", location.z)
        config.set("settings.spawn.yaw", location.yaw)
        config.set("settings.spawn.pitch", location.pitch)
        saveConfig()
    }

    /**
     * Retrieves the spawn location from the configuration.
     *
     * @return The spawn location, or null if not set.
     */
    fun getSpawn(): Location? {
        val world = Bukkit.getWorld(config.getString("settings.spawn.world") ?: return null)
        val x = config.getDouble("settings.spawn.x")
        val y = config.getDouble("settings.spawn.y")
        val z = config.getDouble("settings.spawn.z")
        val yaw = config.getDouble("settings.spawn.yaw").toFloat()
        val pitch = config.getDouble("settings.spawn.pitch").toFloat()
        return Location(world, x, y, z, yaw, pitch)
    }

    /**
     * Saves the settings configuration file.
     */
    private fun saveConfig() {
        ConfigManager.saveSettingsConfig()
    }
}
