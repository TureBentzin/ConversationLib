package de.bentzin.conversationlib.prompt;

import de.bentzin.conversationlib.ConversationContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public class LambdaPrompt implements Prompt {

    private final @NotNull Function<ConversationContext, ComponentLike> promptMessageFunction;
    private final @NotNull Function<ConversationContext, Boolean> blocksForInputFunction;
    private final @NotNull BiFunction<ConversationContext, String, Prompt> nextPromptFunction;

    public LambdaPrompt(@NotNull Function<ConversationContext, ComponentLike> promptMessageFunction,
                        @NotNull Function<ConversationContext, Boolean> blocksForInputFunction,
                        @NotNull BiFunction<ConversationContext, String, Prompt> nextPromptFunction) {
        this.promptMessageFunction = promptMessageFunction;
        this.blocksForInputFunction = blocksForInputFunction;
        this.nextPromptFunction = nextPromptFunction;
    }

    @Override
    public @NotNull ComponentLike getPromptMessage(@NotNull ConversationContext conversationContext) {
        return promptMessageFunction.apply(conversationContext);
    }

    @Override
    public boolean blocksForInput(@NotNull ConversationContext context) {
        return blocksForInputFunction.apply(context);
    }

    @Override
    public @Nullable Prompt getNextPrompt(@NotNull ConversationContext conversationContext, @Nullable String input) {
        return nextPromptFunction.apply(conversationContext, input);
    }
}
