package de.bentzin.conversationlib.prompt;


import de.bentzin.conversationlib.ConversationContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * FixedSetPrompt is the base class for any prompt that requires a fixed set
 * response from the user.
 *
 * @author Ture Bentzin
 * 25.03.2023
 */
public abstract class FixedSetPrompt<E> extends ValidatingPrompt {

    private final @NotNull Function<String, E> interpreter;
    protected @NotNull Set<E> fixedSet;

    /**
     * Creates a FixedSetPrompt from a set of strings.
     * <p>
     * foo = new FixedSetPrompt("bar", "cheese", "panda");
     *
     * @param fixedSet A fixed set of strings, one of which the user must
     *                 type.
     */
    @SafeVarargs
    public FixedSetPrompt(@NotNull Function<String, E> interpreter, @NotNull E... fixedSet) {
        super();
        this.interpreter = interpreter;
        this.fixedSet = Set.of(fixedSet);
    }

    /**
     * Creates a FixedSetPrompt from a set of strings.
     * <p>
     * foo = new FixedSetPrompt("bar", "cheese", "panda");
     *
     * @param fixedSet A fixed set of strings, one of which the user must
     *                 type.
     */
    public FixedSetPrompt(@NotNull Function<String, E> interpreter, @NotNull Collection<E> fixedSet) {
        super();
        this.interpreter = interpreter;
        this.fixedSet = Set.copyOf(fixedSet);
    }

    private FixedSetPrompt(@NotNull Function<String, E> interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
        return fixedSet.contains(interpreter.apply(input));
    }

    public @NotNull Function<String, E> getInterpreter() {
        return interpreter;
    }

    public @NotNull Set<E> getFixedSet() {
        return Set.copyOf(fixedSet);
    }

    protected @NotNull Set<E> getUnsecureFixedSet() {
        return fixedSet;
    }

    /**
     * Utility function to create a formatted string containing all the
     * options declared in the constructor.
     *
     * @return the options formatted like "[bar, cheese, panda]" if bar,
     * cheese, and panda were the options used
     */
    @NotNull
    protected String formatFixedSet() {
        StringJoiner joiner = new StringJoiner(", ");
        fixedSet.forEach(e -> joiner.add(String.valueOf(e)));
        return "[" + joiner + "]";
    }
}