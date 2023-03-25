package de.bentzin.conversationlib.prompt;

import de.bentzin.conversationlib.ConversationContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
@ApiStatus.AvailableSince("1.6")
public abstract class PredicateValidatingPrompt extends ValidatingPrompt {

    private final @NotNull BiPredicate<ConversationContext, String> validator;

    public PredicateValidatingPrompt(@NotNull Predicate<String> validator) {
        this.validator = (context, input) -> validator.test(input);
    }

    public PredicateValidatingPrompt(@NotNull BiPredicate<ConversationContext, String> validator) {
        this.validator = validator;
    }

    @Override
    protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
        return validator.test(context, input);
    }

}
