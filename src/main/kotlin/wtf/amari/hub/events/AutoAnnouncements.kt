package wtf.amari.hub.events

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

class AutoAnnouncements {

    // Map of announcement categories to their respective messages
    private val announcements = mapOf(
        "Discord" to listOf(
            "<#1d90d5>│ <#1db4d5>&lWANT TO STAY UP TO DATE?&r",
            "<#1d90d5>│ &r",
            "<#1d90d5>│ &rbe sure to join our discord server!",
            "<#1d90d5>│ &rIf you want to stay up to date with the latest news,",
            "<#1d90d5>│ &r",
            "<#1d90d5>│ &r<#1db4d5>&ndiscord.domain.com"
        )
        // Add more categories and messages as needed
    )

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
                    val randomAnnouncement = randomCategory?.let { announcements[it]?.joinToString("\n") }

                    // Broadcast the announcement if it exists
                    randomAnnouncement?.let { Bukkit.broadcast(it.mm()) }
                }
            }
        }.runTaskTimer(Hub.instance, 0L, 6000L) // 6000L ticks = 5 minutes
    }
}