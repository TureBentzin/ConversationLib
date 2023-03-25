package de.bentzin.conversationlib.status;

import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public interface ConversationEndListener {

    void conversationEnded(@NotNull ConversationEndCause conversationEndCause);
}
