package com.github.smuddgge.velocityinventory;

import com.github.smuddgge.velocityinventory.dependencys.MiniPlaceholdersAdapter;
import com.github.smuddgge.velocityinventory.dependencys.MiniPlaceholdersDependency;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the message parser.
 * Contains static methods used to help with
 * parsing messages with colours.
 */
public class MessageParser {

    private static final Pattern HEX_PATTERN = Pattern.compile("<#[0-9a-fA-F]{6}");

    /**
     * Used to parse a string, converting legacy colors to mini message colors.
     *
     * @param message The instance of the message.
     * @return The parsed message.
     */
    public static @NotNull String parseToString(@NotNull String message) {
        return MessageParser.convertLegacyToMiniMessage(message);
    }

    /**
     * Used to parse the message and convert it to a component.
     * This will also convert legacy colors to mini message colors.
     *
     * @param message The instance of the message.
     * @param player The instance of the player for parsing mini placeholders.
     * @return The instance of the component.
     */
    public static @NotNull Component parseToComponent(@NotNull String message, @Nullable Player player) {
        return MessageParser.convertMiniMessageToComponent(MessageParser.convertLegacyToMiniMessage(message), player);
    }

    /**
     * Used to convert legacy color codes to mini message tags.
     *
     * @param message The instance of the message.
     * @return The parsed message.
     */
    private static @NotNull String convertLegacyToMiniMessage(@NotNull String message) {
        return MessageParser.convertLegacyHexToMiniMessageHex(message)
                .replace("§", "&") // Ensure there are no legacy symbols.
                .replace("&0", "<reset><black>") // Convert to mini-message.
                .replace("&1", "<reset><dark_blue>")
                .replace("&2", "<reset><dark_green>")
                .replace("&3", "<reset><dark_aqua>")
                .replace("&4", "<reset><dark_red>")
                .replace("&5", "<reset><dark_purple>")
                .replace("&6", "<reset><gold>")
                .replace("&7", "<reset><gray>")
                .replace("&8", "<reset><dark_gray>")
                .replace("&9", "<reset><blue>")
                .replace("&a", "<reset><green>")
                .replace("&b", "<reset><aqua>")
                .replace("&c", "<reset><red>")
                .replace("&d", "<reset><light_purple>")
                .replace("&e", "<reset><yellow>")
                .replace("&f", "<reset><white>")
                .replace("&k", "<obf>")
                .replace("&l", "<b>")
                .replace("&m", "<st>")
                .replace("&n", "<u>")
                .replace("&o", "<i>")
                .replace("&r", "<reset>");
    }

    /**
     * Used to convert legacy hex codes to mini message hex tags.
     *
     * @param message The instance of the message.
     * @return The converted string.
     */
    private static @NotNull String convertLegacyHexToMiniMessageHex(@NotNull String message) {
        final StringBuilder builder = new StringBuilder(
                message.replace("&#", "<#")
        );

        final Matcher matcher = MessageParser.HEX_PATTERN.matcher(builder);

        // Add the end bracket for a mini message.
        matcher.results().forEach(
                result -> builder.insert(result.start() + 8, ">")
        );

        return builder.toString();
    }

    /**
     * Used to convert the mini-message string to a component.
     * This will also parse mini placeholders if installed.
     *
     * @param message The instance of the message.
     * @param player The instance of the player.
     * @return The requested component.
     */
    public static @NotNull Component convertMiniMessageToComponent(@NotNull String message, @Nullable Player player) {

        // Check if the mini placeholders dependency is disabled.
        if (!MiniPlaceholdersDependency.isEnabled()) {
            return MiniMessage.miniMessage().deserialize(message);
        }

        return MiniPlaceholdersAdapter.parseMiniPlaceholders(message, player);
    }
}
