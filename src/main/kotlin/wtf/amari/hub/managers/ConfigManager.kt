package wtf.amari.hub.managers

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm
import java.io.File
import java.io.IOException

/**
 * Manages the configuration operations for the plugin.
 */
object ConfigManager {
    /**
     * Loads a configuration file.
     *
     * @param plugin The plugin instance.
     * @param fileName The name of the configuration file.
     * @return The loaded configuration file.
     */
    fun loadConfig(
        plugin: JavaPlugin,
        fileName: String,
    ): FileConfiguration {
        val file = File(plugin.dataFolder, fileName)
        if (!file.exists()) {
            plugin.saveResource(fileName, false)
        }
        return YamlConfiguration.loadConfiguration(file)
    }

    /**
     * Loads the configuration files.
     */
    fun loadConfigs() {
        Hub.settingsConfig = loadConfig(Hub.instance, "settings.yml")
        Hub.serverSelectorConfig = loadConfig(Hub.instance, "serverSelector.yml")
        Hub.langConfig = loadConfig(Hub.instance, "lang.yml")
    }

    /**
     * Reloads the plugin's configuration files.
     *
     * @param executor The player executing the command.
     * @throws Exception if the configuration reload fails.
     */
    fun reloadConfigs(executor: Player) {
        try {
            Hub.instance.reloadConfig()
            loadConfigs()
            executor.sendMessage("&aAll configs reloaded.".mm())
        } catch (e: Exception) {
            executor.sendMessage("&cFailed to reload configs: ${e.message}".mm())
            throw e
        }
    }

    /**
     * Saves the settings configuration file.
     */
    fun saveSettingsConfig() {
        try {
            Hub.settingsConfig.save(Hub.settingsFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Saves the server selector configuration file.
     */
    fun saveServerSelectorConfig() {
        try {
            Hub.serverSelectorConfig.save(Hub.serverSelectorFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Saves the language configuration file.
     */
    fun saveLangConfig() {
        try {
            Hub.langConfig.save(Hub.langFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
