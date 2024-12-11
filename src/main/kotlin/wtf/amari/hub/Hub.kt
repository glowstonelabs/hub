package wtf.amari.hub

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import me.honkling.commando.spigot.SpigotCommandManager
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import wtf.amari.hub.events.PlayerListener
import wtf.amari.hub.managers.AutoAnnouncementManager
import wtf.amari.hub.managers.ConfigManager
import wtf.amari.hub.utils.PlaceHolders
import wtf.amari.hub.utils.fancyLog
import java.io.File

/**
 * Main class for the Hub plugin.
 */
class Hub : JavaPlugin() {

    companion object {
        lateinit var instance: Hub
            private set

        private val scope = CoroutineScope(Dispatchers.Default)

        lateinit var settingsConfig: FileConfiguration
        lateinit var serverSelectorConfig: FileConfiguration
        lateinit var langConfig: FileConfiguration

        lateinit var settingsFile: File
        lateinit var serverSelectorFile: File
        lateinit var langFile: File
    }

    private val commandPackages = listOf(
        "wtf.amari.hub.commands"
    )

    private val listeners = listOf { PlayerListener() }

    override fun onEnable() {
        instance = this
        initializePlugin()
        fancyLog("Hub plugin has been enabled successfully!", "SUCCESS")
    }

    override fun onDisable() {
        fancyLog("Hub plugin has been disabled.", "ERROR")
        cleanupResources()
    }

    /**
     * Initializes the plugin by setting up configurations, commands, events, and placeholders.
     */
    private fun initializePlugin() {
        setupConfig()
        registerCommands()
        registerEvents()
        registerPlaceholders()
        AutoAnnouncementManager()
    }

    /**
     * Registers commands using the SpigotCommandManager.
     */
    private fun registerCommands() {
        val commandManager = SpigotCommandManager(this)
        try {
            commandPackages.forEach { commandManager.registerCommands(it) }
            fancyLog("Commands registered successfully.", "SUCCESS")
        } catch (e: Exception) {
            fancyLog("Failed to register commands: ${e.message}", "ERROR")
        }
    }

    /**
     * Registers event listeners.
     */
    private fun registerEvents() {
        listeners.forEach { listenerSupplier ->
            try {
                val listener = listenerSupplier()
                server.pluginManager.registerEvents(listener, this)
                fancyLog("${listener::class.simpleName} registered successfully.", "SUCCESS")
            } catch (e: Exception) {
                fancyLog("Failed to register listener: ${e.message}", "ERROR")
            }
        }
    }

    /**
     * Sets up the configuration files.
     */
    private fun setupConfig() {
        dataFolder.mkdirs()
        settingsFile = File(dataFolder, "settings.yml")
        settingsConfig = ConfigManager.loadConfig(this, "settings.yml")
        serverSelectorFile = File(dataFolder, "serverSelector.yml")
        serverSelectorConfig = ConfigManager.loadConfig(this, "serverSelector.yml")
        langFile = File(dataFolder, "lang.yml")
        langConfig = ConfigManager.loadConfig(this, "lang.yml")
        fancyLog("Configs loaded successfully.", "SUCCESS")
    }

    /**
     * Registers PlaceholderAPI placeholders if the plugin is available.
     */
    private fun registerPlaceholders() {
        server.pluginManager.getPlugin("PlaceholderAPI")?.let {
            PlaceHolders().register()
            fancyLog("PlaceholderAPI placeholders registered successfully.", "SUCCESS")
        }
    }

    /**
     * Cleans up resources when the plugin is disabled.
     */
    private fun cleanupResources() {
        ConfigManager.saveSettingsConfig()
        ConfigManager.saveServerSelectorConfig()
        ConfigManager.saveLangConfig()
    }
}