package wtf.amari.hub.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit.getConsoleSender

val mm = MiniMessage.miniMessage()

/**
 * Object containing color codes used in the plugin.
 */
object Colors {
    private val colors =
        mapOf(
            "PRIMARY" to "#3498db", // Blue
            "SECONDARY" to "#2ecc71", // Green
            "ERROR" to "#e74c3c", // Red
            "SUCCESS" to "#2ecc71", // Green
            "WARNING" to "#f1c40f", // Yellow
            "INFO" to "#3498db", // Blue
            "BROADCAST" to "#9b59b6", // Purple
            "SYSTEM" to "#34495e", // Dark Blue
        )

    /**
     * Retrieves the color code for the given name.
     *
     * @param name The name of the color.
     * @return The color code as a string.
     */
    fun getColor(name: String): String = colors[name] ?: "#ffffff" // default to white if not found
}

/**
 * Logs a message to the console with a specific color based on the level.
 *
 * @param message The message to log.
 * @param level The level of the message (e.g., "ERROR", "SUCCESS").
 */
fun fancyLog(
    message: String,
    level: String,
) {
    val color = Colors.getColor(level)
    val boxedMessage = message.box(borderColor = color, textColor = color)
    getConsoleSender().sendMessage(boxedMessage)
}

/**
 * Converts a string with legacy color codes to a MiniMessage component.
 *
 * @return The converted MiniMessage component.
 */
fun String.mm(): Component =
    mm
        .deserialize(this.convertLegacyColors())
        .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)

/**
 * Applies a gradient effect to the string.
 *
 * @return The string with a gradient effect.
 */
fun String.mainGradient(): Component = "<gradient:#b8ecff:#77daff>$this</gradient>".mm()

/**
 * Converts legacy color codes to MiniMessage format.
 *
 * @return The string with MiniMessage color codes.
 */
fun String.convertLegacyColors(): String =
    this
        .replace("&0", "&r<black>")
        .replace("&1", "&r<dark_blue>")
        .replace("&2", "&r<dark_green>")
        .replace("&3", "&r<dark_aqua>")
        .replace("&4", "&r<dark_red>")
        .replace("&5", "&r<dark_purple>")
        .replace("&6", "&r<gold>")
        .replace("&7", "&r<gray>")
        .replace("&8", "&r<dark_gray>")
        .replace("&9", "&r<blue>")
        .replace("&a", "&r<green>")
        .replace("&b", "&r<aqua>")
        .replace("&c", "&r<red>")
        .replace("&d", "&r<light_purple>")
        .replace("&e", "&r<yellow>")
        .replace("&f", "&r<white>")
        .replace("&k", "<obf>")
        .replace("&l", "<b>")
        .replace("&m", "<st>")
        .replace("&n", "<u>")
        .replace("&o", "<i>")
        .replace("&r", "<reset>")

/**
 * Converts legacy color codes to the new format.
 *
 * @return The string with new color codes.
 */
fun String.convertNewColors(): String =
    this
        .replace("&0", "§0")
        .replace("&1", "§1")
        .replace("&2", "§2")
        .replace("&3", "§3")
        .replace("&4", "§4")
        .replace("&5", "§5")
        .replace("&6", "§6")
        .replace("&7", "§7")
        .replace("&8", "§8")
        .replace("&9", "§9")
        .replace("&a", "§a")
        .replace("&b", "§b")
        .replace("&c", "§c")
        .replace("&d", "§d")
        .replace("&e", "§e")
        .replace("&f", "§f")
        .replace("&k", "§k")
        .replace("&l", "§l")
        .replace("&m", "§m")
        .replace("&n", "§n")
        .replace("&o", "§o")
        .replace("&r", "§r")

/**
 * Applies a complex gradient effect to the string.
 *
 * @param colors The colors to use in the gradient.
 * @return The string with a complex gradient effect.
 */
fun String.complexGradient(vararg colors: String): Component {
    val gradient = colors.joinToString(":")
    return "<gradient:$gradient>$this</gradient>".mm()
}

/**
 * Adds an emoji to the string.
 *
 * @param emoji The emoji to add.
 * @return The string with the emoji.
 */
fun String.withEmoji(emoji: String): Component = "$this $emoji".mm()

/**
 * Applies a gradient effect to the string.
 *
 * @param colors The colors to use in the gradient.
 * @return The string with a gradient effect.
 */
fun String.gradientText(vararg colors: String): Component {
    val gradient = colors.joinToString(":")
    return "<gradient:$gradient>$this</gradient>".mm()
}

/**
 * Applies a rainbow effect to the string.
 *
 * @return The string with a rainbow effect.
 */
fun String.rainbow(): Component = "<rainbow>$this</rainbow>".mm()

/**
 * Formats the string as a prison title.
 *
 * @param primary The primary color.
 * @param secondary The secondary color.
 * @return The formatted prison title.
 */
fun String.prisonTitle(
    primary: String = Colors.getColor("PRIMARY"),
    secondary: String = Colors.getColor("SECONDARY"),
): Component = "<$primary>✦ <$secondary>$this".mm()

/**
 * Adds hover text to the string.
 *
 * @param hoverText The text to show on hover.
 * @return The string with hover text.
 */
fun String.withHover(hoverText: String): Component = "<hover:show_text:'${hoverText.replace("'", "\\'")}'>$this</hover>".mm()

/**
 * Adds a command to the string.
 *
 * @param command The command to run.
 * @return The string with the command.
 */
fun String.withCommand(command: String): Component = "<click:run_command:'${command.replace("'", "\\'")}'>$this</click>".mm()

/**
 * Adds a suggestion command to the string.
 *
 * @param suggestion The suggestion command.
 * @return The string with the suggestion command.
 */
fun String.withSuggest(suggestion: String): Component = "<click:suggest_command:'${suggestion.replace("'", "\\'")}'>$this</click>".mm()

/**
 * Adds a URL to the string.
 *
 * @param url The URL to open.
 * @return The string with the URL.
 */
fun String.withUrl(url: String): Component = "<click:open_url:'${url.replace("'", "\\'")}'>$this</click>".mm()

/**
 * Creates a progress bar component.
 *
 * @param current The current progress.
 * @param max The maximum progress.
 * @param length The length of the progress bar.
 * @param filledSymbol The symbol for filled progress.
 * @param emptySymbol The symbol for empty progress.
 * @param filledColor The color for filled progress.
 * @param emptyColor The color for empty progress.
 * @return The progress bar component.
 */
fun createProgressBar(
    current: Int,
    max: Int,
    length: Int = 10,
    filledSymbol: String = "■",
    emptySymbol: String = "□",
    filledColor: String = Colors.getColor("PRIMARY"),
    emptyColor: String = "gray",
): Component {
    val progress = (current.toFloat() / max * length).toInt()
    return "<$filledColor>${filledSymbol.repeat(progress)}<$emptyColor>${emptySymbol.repeat(length - progress)}".mm()
}

object Prefix {
    val ERROR = "<${Colors.getColor("ERROR")}>✖ ".mm()
    val SUCCESS = "<${Colors.getColor("SUCCESS")}>✔ ".mm()
    val INFO = "<${Colors.getColor("INFO")}>✦ ".mm()
    val WARNING = "<${Colors.getColor("WARNING")}>⚠ ".mm()
    val ADMIN = "<${Colors.getColor("ERROR")}>⚡ ".mm()
    val BROADCAST = "<${Colors.getColor("BROADCAST")}>» ".mm()
    val SYSTEM = "<${Colors.getColor("SYSTEM")}>✦ ".mm()
}

/**
 * Formats the string as a list item.
 *
 * @param bullet The bullet symbol.
 * @param bulletColor The color of the bullet.
 * @param textColor The color of the text.
 * @return The formatted list item.
 */
fun String.listItem(
    bullet: String = "▪",
    bulletColor: String = "gray",
    textColor: String = "white",
): Component = "<$bulletColor>$bullet <$textColor>$this".mm()

/**
 * Creates a stats line component.
 *
 * @param label The label of the stat.
 * @param value The value of the stat.
 * @param labelColor The color of the label.
 * @param valueColor The color of the value.
 * @return The formatted stats line.
 */
fun createStatsLine(
    label: String,
    value: String,
    labelColor: String = "gray",
    valueColor: String = "white",
): Component = "<$labelColor>$label: <$valueColor>$value".mm()

/**
 * Applies a fade effect to the string.
 *
 * @param fromColor The starting color.
 * @param toColor The ending color.
 * @return The string with a fade effect.
 */
fun String.fade(
    fromColor: String = Colors.getColor("PRIMARY"),
    toColor: String = Colors.getColor("SECONDARY"),
): Component = "<gradient:$fromColor:$toColor>$this</gradient>".mm()

/**
 * Formats the string as a boxed message.
 *
 * @param borderColor The color of the border.
 * @param textColor The color of the text.
 * @param padding The padding inside the box.
 * @return The formatted boxed message.
 */
fun String.box(
    borderColor: String = Colors.getColor("PRIMARY"),
    textColor: String = "white",
    padding: Int = 2,
): Component {
    val width = this.length + 4 + padding * 2 // Add padding for the box and extra spaces
    val topBottomBorder = "─".repeat(width)
    val newLine = System.lineSeparator()
    return """
$newLine<$borderColor>┌$topBottomBorder┐
<$textColor>${" ".repeat(padding)}${this.center(width - padding * 2)}${" ".repeat(padding)}
<$borderColor>└$topBottomBorder┘
        """.trimIndent().mm()
}

/**
 * Centers the string within a given width.
 *
 * @param width The width to center within.
 * @return The centered string.
 */
private fun String.center(width: Int): String {
    if (this.length >= width) return this
    val padding = (width - this.length) / 2
    return " ".repeat(padding) + this + " ".repeat(width - this.length - padding)
}

/**
 * Formats a number with commas and two decimal places.
 *
 * @return The formatted number.
 */
fun Number.format(): String = String.format("%,.2f", this.toDouble())

/**
 * Converts a number to a shorthand format (e.g., 1.5K, 2.3M).
 *
 * @return The shorthand formatted number.
 */
fun Number.shorthand(): String {
    val number = this.toLong()
    return when {
        number >= 1_000_000_000_000 -> String.format("%.1fT", number / 1_000_000_000_000.0)
        number >= 1_000_000_000 -> String.format("%.1fB", number / 1_000_000_000.0)
        number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000.0)
        number >= 1_000 -> String.format("%.1fK", number / 1_000.0)
        else -> number.toString()
    }
}

/**
 * Creates a line component with a specified color and symbol.
 *
 * @param color The color of the line.
 * @param symbol The symbol to use for the line.
 * @return The line component.
 */
fun createLine(
    color: String = Colors.getColor("PRIMARY"),
    symbol: String = "▬",
): Component = "<$color>${symbol.repeat(40)}".mm()

/**
 * Formats a double as a money string.
 *
 * @return The formatted money string.
 */
fun Double.money(): String = String.format("$%,.2f", this)
