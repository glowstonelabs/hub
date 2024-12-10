package wtf.amari.hub.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Loads a configuration file.
 * @param plugin The plugin instance.
 * @param fileName The name of the configuration file.
 * @return The loaded configuration file.
 */
fun loadConfig(plugin: JavaPlugin, fileName: String): FileConfiguration {
    val file = File(plugin.dataFolder, fileName)
    if (!file.exists()) {
        plugin.saveResource(fileName, false)
    }
    return YamlConfiguration.loadConfiguration(file)
}