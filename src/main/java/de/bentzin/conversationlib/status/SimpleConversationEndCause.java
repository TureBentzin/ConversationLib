package de.bentzin.conversationlib.status;

import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public class SimpleConversationEndCause implements ConversationEndCause{

    private final @NotNull String message;
    private final @NotNull Type type;

    public SimpleConversationEndCause(@NotNull String message, @NotNull Type type) {
        this.message = message;
        this.type = type;
    }

    @Override
    public @NotNull String getMessage() {
        return message;
    }

    @Override
    public @NotNull Type getType() {
        return type;
    }
}
