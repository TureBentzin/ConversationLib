package de.bentzin.conversationlib.status;


/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public class ConversationSkipped extends SimpleConversationEndCause{
    public ConversationSkipped() {
        super("The conversation was skipped!", Type.ABORTED);
    }
}
