package de.bentzin.conversationlib;

import de.bentzin.conversationlib.prompt.Prompt;
import de.bentzin.conversationlib.status.ConversationEndCause;
import de.bentzin.conversationlib.status.ConversationEndListener;
import de.bentzin.conversationlib.status.ConversationEnded;
import de.bentzin.conversationlib.status.UnknownConversationEndCause;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public class Conversation {
    private final @NotNull ConversationContext conversationContext;
    private final @NotNull Prompt firstPrompt;
    private final boolean localEchoEnabled;
    private final @Nullable ConversationPrefix prefix;
    private final @NotNull Map<Object, Object> sessionData;
    private final @NotNull Predicate<Converser> converserFilter;
    private final @NotNull Component illegalConverser;
    private final @NotNull List<ConversationEndListener> endListeners;
    private boolean active = false;
    private @Nullable Prompt currentPrompt;

    public Conversation(@NotNull Prompt firstPrompt, boolean localEchoEnabled, @Nullable ConversationPrefix prefix,
                            @NotNull Map<Object, Object> sessionData, @NotNull Predicate<Converser> converserFilter,
                            @NotNull Component illegalConverser, @NotNull List<ConversationEndListener> endListeners,
                            @NotNull ConversationContext conversationContext) {
        this.firstPrompt = firstPrompt;
        this.localEchoEnabled = localEchoEnabled;
        this.prefix = prefix;
        this.sessionData = sessionData;
        this.converserFilter = converserFilter;
        this.illegalConverser = illegalConverser;
        this.endListeners = endListeners;
        this.conversationContext = conversationContext;
    }

    public @NotNull Prompt getFirstPrompt() {
        return firstPrompt;
    }

    public boolean isLocalEchoEnabled() {
        return localEchoEnabled;
    }

    public @Nullable ConversationPrefix getPrefix() {
        return prefix;
    }

    public @NotNull Map<Object, Object> getSessionData() {
        return sessionData;
    }

    public @NotNull Predicate<Converser> getConverserFilter() {
        return converserFilter;
    }

    public @NotNull Component getIllegalConverser() {
        return illegalConverser;
    }

    public @NotNull List<ConversationEndListener> getEndListeners() {
        return endListeners;
    }


    public boolean isActive() {
        return active;
    }


    public @NotNull Converser getTarget() {
        return getConversationContext().target();
    }


    public void begin() {
        active = true;
        currentPrompt = firstPrompt;
    }


    public void end(@NotNull ConversationEndCause conversationEndCause) {
        active = false;
        endListeners.forEach(conversationEndListener -> conversationEndListener.conversationEnded(conversationEndCause));
    }


    public void end() {
        end(new UnknownConversationEndCause());
    }


    public void acceptInput(@NotNull String input) {
        if (!active) return;
        if (currentPrompt != null) {

            // Echo the user's input
            if (localEchoEnabled) {
                getTarget().sendMessage(Component.text("> " + input));
            }

            // Not abandoned, output the next prompt
            currentPrompt = currentPrompt.getNextPrompt(conversationContext, input);
            outputNextPrompt();
        }

    }

    public @NotNull ConversationContext getConversationContext() {
        return conversationContext;
    }

    private void outputNextPrompt() {
       outputNextPrompt(null);
    }

    @ApiStatus.Internal
    private void outputNextPrompt(@Nullable String fakeInput) {
        if (currentPrompt == null) {
            end(new ConversationEnded(conversationContext));
        } else {
            if(fakeInput != null) {
                getTarget().sendMessage(Component.text("> " + fakeInput));
            }
            assemble(currentPrompt).ifPresent(component -> getTarget().sendMessage(component));
            if (!currentPrompt.blocksForInput(conversationContext)) {
                currentPrompt = currentPrompt.getNextPrompt(conversationContext, fakeInput);
                outputNextPrompt();
            }
        }
    }

    protected @NotNull ConversationPrefix providePrefix() {
        if(prefix != null) return prefix;
        else return context -> Component.empty();
    }

    protected @NotNull Optional<Component> assemble(@Nullable Prompt prompt) {
        if(prompt == null) {
            return Optional.empty();
        }
        return Optional.of(providePrefix().getPrefix(conversationContext).append(prompt.getPromptMessage(conversationContext)));
    }
}
