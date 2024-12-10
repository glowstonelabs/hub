package wtf.amari.hub

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import me.honkling.commando.spigot.SpigotCommandManager
import org.bukkit.plugin.java.JavaPlugin
import wtf.amari.hub.events.AutoAnnouncements
import wtf.amari.hub.events.PlayerListener
import wtf.amari.hub.utils.PlaceHolders
import wtf.amari.hub.utils.fancyLog

/**
 * Main class for the Hub plugin.
 */
class Hub : JavaPlugin() {

    companion object {
        lateinit var instance: Hub
            private set

        private val scope = CoroutineScope(Dispatchers.Default)
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
    }

    /**
     * Initializes the plugin by setting up configuration, registering commands, events, and placeholders.
     */
    private fun initializePlugin() {
        setupConfig()
        registerCommands()
        registerEvents()
        registerPlaceholders()
        AutoAnnouncements()
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
     * Sets up the configuration by creating the data folder, saving the default config, and reloading the config.
     */
    private fun setupConfig() {
        dataFolder.mkdirs()
        saveDefaultConfig()
        reloadConfig()
        fancyLog("Config loaded successfully.", "SUCCESS")
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
}