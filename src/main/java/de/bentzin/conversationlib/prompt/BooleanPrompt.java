package de.bentzin.conversationlib.prompt;

import de.bentzin.conversationlib.ConversationContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * BooleanPrompt is the base class for any prompt that requires a boolean
 * response from the user.
 * @author Ture Bentzin
 * 25.03.2023
 */
public abstract class BooleanPrompt extends ValidatingPrompt {

    private static final @NotNull Set<String> TRUE_INPUTS = Set.of("true", "on", "yes", "y", "1", "right", "correct", "valid", "ja", "j", "1b", "go", "run", "proceed");
    private static final @NotNull Set<String> FALSE_INPUTS = Set.of("false", "off", "no", "n", "0", "wrong", "incorrect", "invalid", "nein", "0b", "abort", "kill", "go-around");
    private static final @NotNull Set<String> VALID_INPUTS;

    static {
        VALID_INPUTS = new HashSet<>(TRUE_INPUTS);
        VALID_INPUTS.addAll(FALSE_INPUTS);
    }

    public BooleanPrompt() {
        super();
    }

    @Override
    protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
        return VALID_INPUTS.contains(input.toLowerCase(Locale.ROOT));
    }

    @Nullable
    @Override
    protected Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
        return acceptValidatedInput(context, TRUE_INPUTS.contains(input.toLowerCase(Locale.ROOT)));
    }

    /**
     * Override this method to perform some action with the user's boolean
     * response.
     *
     * @param context Context information about the conversation.
     * @param input The user's boolean response.
     * @return The next {@link Prompt} in the prompt graph.
     */
    @Nullable
    protected abstract Prompt acceptValidatedInput(@NotNull ConversationContext context, boolean input);
}
