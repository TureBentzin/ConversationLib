package de.bentzin.conversationlib;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * A ConversationPrefix implementation prepends all output from the
 * conversation to the player. The ConversationPrefix can be used to display
 * the plugin name or conversation status as the conversation evolves.
 *
 * @author Ture Bentzin
 * 25.03.2023
 */
public interface ConversationPrefix {

    /**
     * Gets the prefix to use before each message to the converser!
     *
     * @param context Context information about the conversation.
     * @return The prefix text.
     */
    @NotNull Component getPrefix(@NotNull ConversationContext context);
}
