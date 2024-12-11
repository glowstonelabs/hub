package wtf.amari.hub.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Utility object for loading and managing configuration files.
 */
object ConfigManager {

    /**
     * Loads a configuration file.
     *
     * @param plugin The plugin instance.
     * @param fileName The name of the configuration file.
     * @return The loaded configuration file.
     * @throws IllegalArgumentException if the configuration file cannot be loaded.
     */
    fun loadConfig(plugin: JavaPlugin, fileName: String): FileConfiguration {
        val file = File(plugin.dataFolder, fileName)
        if (!file.exists()) {
            plugin.saveResource(fileName, false)
        }
        return YamlConfiguration.loadConfiguration(file)
    }

    /**
     * Validates the configuration to ensure all required keys are present.
     *
     * @param config The configuration to validate.
     * @param keys The keys to check in the configuration.
     * @throws IllegalArgumentException if any key is missing.
     */
    fun validateConfig(config: FileConfiguration, vararg keys: String) {
        keys.forEach { key ->
            require(config.contains(key)) { "Missing required key: $key in ${config.name}.yml" }
        }
    }
}