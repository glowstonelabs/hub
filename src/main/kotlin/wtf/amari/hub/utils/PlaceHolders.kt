package wtf.amari.hub.utils

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class PlaceHolders : PlaceholderExpansion() {
    override fun getIdentifier() = "hub"

    override fun getAuthor() = "Amari"

    override fun getVersion() = "1.0"

    override fun onPlaceholderRequest(
        player: Player?,
        identifier: String,
    ): String {
        if (player == null) return ""
        return when (identifier) {
            "player" -> player.name
            else -> ""
        }
    }
}
