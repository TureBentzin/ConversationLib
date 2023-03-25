package de.bentzin.conversationlib.prompt;

import de.bentzin.conversationlib.ConversationContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
@ApiStatus.AvailableSince("1.6")
public abstract class ClazzPrompt extends ValidatingPrompt {

    @Override
    protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
        try {
            Class.forName(input);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
        try {
            return acceptClazzInput(context, Class.forName(input));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract @Nullable Prompt acceptClazzInput(@NotNull ConversationContext context, @NotNull Class<?> input);
}
