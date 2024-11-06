package wtf.amari.hub

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import me.honkling.commando.spigot.SpigotCommandManager
import org.bukkit.plugin.java.JavaPlugin
import wtf.amari.hub.events.AutoAnnouncements
import wtf.amari.hub.events.PlayerListener
import wtf.amari.hub.utils.PlaceHolders
import wtf.amari.hub.utils.fancyLog

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

    private fun initializePlugin() {
        setupConfig()
        registerCommands()
        registerEvents()
        registerPlaceholders()
        AutoAnnouncements()
    }

    private fun registerCommands() {
        val commandManager = SpigotCommandManager(this)
        try {
            commandPackages.forEach { commandManager.registerCommands(it) }
            fancyLog("Commands registered successfully.", "SUCCESS")
        } catch (e: Exception) {
            fancyLog("Failed to register commands: ${e.message}", "ERROR")
        }
    }

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

    private fun setupConfig() {
        dataFolder.mkdirs()
        saveDefaultConfig()
        reloadConfig()
        fancyLog("Config loaded successfully.", "SUCCESS")
    }

    private fun registerPlaceholders() {
        server.pluginManager.getPlugin("PlaceholderAPI")?.let {
            PlaceHolders().register()
            fancyLog("PlaceholderAPI placeholders registered successfully.", "SUCCESS")
        }
    }
}