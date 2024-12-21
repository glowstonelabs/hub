package wtf.amari.hub.managers

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import wtf.amari.hub.Hub
import wtf.amari.hub.utils.mm

/**
 * Handles automatic announcements for the server.
 */
class AutoAnnouncementManager {
    private val config = Hub.langConfig

    private val announcements: Map<String, List<String>> =
        config.getConfigurationSection("announcements")?.getKeys(false)?.associateWith { category ->
            config.getStringList("announcements.$category")
        } ?: emptyMap()

    init {
        validateConfig()
        startAnnouncements()
    }

    /**
     * Validates the configuration to ensure all required sections and values are present.
     *
     * @throws IllegalArgumentException if the configuration is invalid.
     */
    private fun validateConfig() {
        require(config.isConfigurationSection("announcements")) { "Missing required key: announcements in lang.yml" }
        announcements.forEach { (category, messages) ->
            require(messages.isNotEmpty()) { "Missing required key: announcements.$category in lang.yml" }
        }
    }

    /**
     * Starts the announcement task that periodically sends random announcements to online players.
     */
    private fun startAnnouncements() {
        object : BukkitRunnable() {
            override fun run() {
                if (Bukkit.getOnlinePlayers().isNotEmpty()) {
                    val randomCategory = announcements.keys.randomOrNull()
                    val randomAnnouncement = randomCategory?.let { announcements[it]?.joinToString("\n") }

                    randomAnnouncement?.let { announcement ->
                        val clickableAnnouncement = formatAnnouncement(announcement)
                        Bukkit.getOnlinePlayers().forEach { player ->
                            player.sendMessage(clickableAnnouncement)
                        }
                    }
                }
            }
        }.runTaskTimer(Hub.instance, 0L, 6000L)
    }

    /**
     * Formats the announcement string by replacing color/format codes and URLs.
     *
     * @param announcement The raw announcement string.
     * @return The formatted announcement string.
     */
    private fun formatAnnouncement(announcement: String): Component =
        announcement
            .replace("&r", "<reset>")
            .replace("&n", "<u>") // Handle color/format codes
            .replace(
                Regex("""[a-zA-Z0-9.-]+(?:\.[a-zA-Z]{2,})(/[a-zA-Z0-9._~-]*)?"""),
            ) { matchResult ->
                val url = matchResult.value
                "<click:open_url:'https://$url'><#1d90d5><u>$url</u></click>"
            }.replace("\n", "<br>") // Fix newlines
            .mm()
}
