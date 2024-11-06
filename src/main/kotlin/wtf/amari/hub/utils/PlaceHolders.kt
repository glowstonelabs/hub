package wtf.amari.hub.utils

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class PlaceHolders : PlaceholderExpansion() {

    override fun getIdentifier() = "prison"
    override fun getAuthor() = "Amari"
    override fun getVersion() = "1.0"

    /**
     * Handles placeholder requests for player-specific data.
     * @param player The player for whom the placeholder is requested.
     * @param identifier The placeholder identifier.
     * @return The placeholder value as a string.
     */
    override fun onPlaceholderRequest(player: Player?, identifier: String): String {
        if (player == null) return ""
        return when (identifier) {
            "player" -> (player.name)
            else -> ""
        }
    }
}