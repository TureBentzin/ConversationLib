package de.bentzin.conversationlib.prompt;

import de.bentzin.conversationlib.ConversationContext;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public class StaticPrompt implements Prompt{

    private final boolean blocksForInput;
    private final @NotNull Component promptMessage;
    private final @Nullable Prompt nextPrompt;

    public StaticPrompt(boolean blocksForInput, @NotNull Component promptMessage, @Nullable Prompt nextPrompt) {

        this.blocksForInput = blocksForInput;
        this.promptMessage = promptMessage;
        this.nextPrompt = nextPrompt;
    }

    @Override
    public @NotNull Component getPromptMessage(@NotNull ConversationContext conversationContext) {
        return promptMessage;
    }

    @Override
    public boolean blocksForInput(@NotNull ConversationContext context) {
        return blocksForInput;
    }

    @Override
    public @Nullable Prompt getNextPrompt(@NotNull ConversationContext conversationContext, @Nullable String input) {
        return nextPrompt;
    }
}
