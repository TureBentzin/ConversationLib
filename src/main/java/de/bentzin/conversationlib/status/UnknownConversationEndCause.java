package de.bentzin.conversationlib.status;

import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public class UnknownConversationEndCause implements ConversationEndCause{
    @Override
    public @NotNull String getMessage() {
        return "unknown";
    }

    @Override
    public @NotNull Type getType() {
        return Type.UNKNOWN;
    }
}
