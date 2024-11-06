package wtf.amari.hub.events

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

class AutoAnnouncements {

    private val config = Hub.instance.config

    // Map of announcement categories to their respective messages
    private val announcements: Map<String, List<String>> =
        config.getConfigurationSection("announcements")?.getKeys(false)?.associateWith { category ->
            config.getStringList("announcements.$category")
        } ?: emptyMap()

    init {
        startAnnouncements()
    }

    /**
     * Starts the announcement task that broadcasts a random announcement
     * from a random category every 5 minutes.
     */
    private fun startAnnouncements() {
        object : BukkitRunnable() {
            override fun run() {
                // Check if there are any players online
                if (Bukkit.getOnlinePlayers().isNotEmpty()) {
                    // Select a random category and its corresponding messages
                    val randomCategory = announcements.keys.randomOrNull()
                    val randomAnnouncement = randomCategory?.let { announcements[it]?.randomOrNull() }

                    // Broadcast the announcement if it exists
                    randomAnnouncement?.let { Bukkit.broadcast(it.mm()) }
                }
            }
        }.runTaskTimer(Hub.instance, 0L, 6000L) // 6000L ticks = 5 minutes
    }
}