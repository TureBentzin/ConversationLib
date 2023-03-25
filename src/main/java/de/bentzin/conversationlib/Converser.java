package de.bentzin.conversationlib;

import de.bentzin.conversationlib.status.ConversationSkipped;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Predicate;

/**
 * This represents someone that can be part of a conversation!
 *
 * @author Ture Bentzin
 * 25.03.2023
 */
public class Converser {

    private final @NotNull Audience target;
    private final @NotNull ArrayDeque<Conversation> conversations;

    public Converser(@NotNull Audience target) {
        this.target = target;
        conversations = new ArrayDeque<>();
    }

    public Converser(@NotNull Audience target, @NotNull Queue<Conversation> conversations) {
        this.target = target;
        this.conversations = new ArrayDeque<>(conversations);
    }

    public @NotNull Audience getTarget() {
        return target;
    }

    public @NotNull Queue<Conversation> getConversations() {
        return conversations.clone();
    }

    public @NotNull Optional<Conversation> getCurrentConversation() {
        return Optional.ofNullable(getConversations().peek());
    }

    public @NotNull Optional<Conversation> next() {
        if (!conversations.isEmpty()) {
            Conversation pop = conversations.pop();
            pop.end(new ConversationSkipped());
            getCurrentConversation().ifPresent(Conversation::begin);
            return Optional.of(pop);
        }
        return Optional.empty();
    }

    public void clear() {
        conversations.clear();
    }

    public synchronized void filter(@NotNull Predicate<Conversation> filter) {
        ArrayDeque<Conversation> clone = conversations.clone();
        clone.stream().filter(Predicate.not(filter)).toList().forEach(conversations::remove);
    }

    public synchronized void remove(@NotNull Conversation conversation) {
        conversations.remove(conversation);
    }

    public void inputText(@NotNull String input) {
        getCurrentConversation().ifPresent(conversation -> conversation.acceptInput(input));
    }

    @Override
    public boolean equals(@NotNull Object obj) {
        if (obj instanceof Converser converser) {
            return converser.getTarget().equals(getTarget());
        }
        return false;
    }
}
