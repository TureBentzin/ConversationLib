package de.bentzin.conversationlib.prompt;

import de.bentzin.conversationlib.ConversationContext;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public interface Prompt {

    @Nullable Prompt END_OF_CONVERSATION = null;

    @NotNull Component getPromptMessage(@NotNull ConversationContext conversationContext);

    /**
     * Checks to see if this prompt implementation should wait for user input
     * or immediately display the next prompt.
     */
    boolean blocksForInput(@NotNull ConversationContext context);

    @Nullable Prompt getNextPrompt(@NotNull ConversationContext conversationContext, @Nullable String input);
}
