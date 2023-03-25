package de.bentzin.conversationlib.status;

import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public interface ConversationEndCause {

    @NotNull String getMessage();

    @NotNull Type getType();

    enum Type {
        END_OF_CONVERSATION,
        ABORTED,
        UNKNOWN,
    }
}
