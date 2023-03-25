package de.bentzin.conversationlib.status;

import de.bentzin.conversationlib.ConversationContext;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public class ConversationEnded extends SimpleConversationEndCause{
    public ConversationEnded(@NotNull ConversationContext conversationContext) {
        super("conversation ended with: " + conversationContext, Type.END_OF_CONVERSATION);
    }
}
