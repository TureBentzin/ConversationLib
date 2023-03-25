package de.bentzin.conversationlib;

import de.bentzin.conversationlib.status.ConversationEndCause;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public interface Conversation {

    boolean isActive();

    @NotNull Converser getTarget();

    void begin();
    void end(@NotNull ConversationEndCause conversationEndCause);
    void end();

    void acceptInput(@NotNull String input);
}
