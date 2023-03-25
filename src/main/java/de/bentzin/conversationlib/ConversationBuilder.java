package de.bentzin.conversationlib;

import de.bentzin.conversationlib.manager.ConversationManager;
import de.bentzin.conversationlib.prompt.Prompt;
import de.bentzin.conversationlib.status.ConversationEndListener;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public class ConversationBuilder {
    private final @NotNull ConversationManager manager;
    private @Nullable Prompt firstPrompt = null;
    private boolean localEchoEnabled = true;
    private @Nullable ConversationPrefix prefix = null;
    private @NotNull Map<Object, Object> initialSessionData = new HashMap<>();
    private @NotNull Predicate<Converser> converserFilter = converser -> true;
    private @NotNull Component illegalConverser = Component.text("Illegal converser - no further information!");
    private @NotNull List<ConversationEndListener> endListeners = new ArrayList<>();

    public ConversationBuilder(@NotNull ConversationManager manager) {
        this.manager = manager;
    }

    public boolean isLocalEchoEnabled() {
        return localEchoEnabled;
    }

    public @Nullable ConversationPrefix getPrefix() {
        return prefix;
    }

    public @NotNull Map<Object, Object> getInitialSessionData() {
        return initialSessionData;
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

    public @Nullable Prompt getFirstPrompt() {
        return firstPrompt;
    }

    public @NotNull ConversationBuilder firstPrompt(@NotNull Prompt firstPrompt) {
        this.firstPrompt = firstPrompt;
        return this;
    }

    public @NotNull ConversationBuilder localEchoEnabled(boolean localEchoEnabled) {
        this.localEchoEnabled = localEchoEnabled;
        return this;
    }

    public @NotNull ConversationBuilder prefix(@Nullable ConversationPrefix conversationPrefix) {
        this.prefix = conversationPrefix;
        return this;
    }

    public @NotNull ConversationBuilder initialSessionData(@NotNull Map<Object,Object> initialSessionData) {
        this.initialSessionData = initialSessionData;
        return this;
    }

    public @NotNull ConversationBuilder converserFilter(@NotNull Predicate<Converser> converserFilter) {
        this.converserFilter = converserFilter;
        return this;
    }

    public @NotNull ConversationBuilder illegalConverserMessage(@NotNull Component illegalConverserMessage) {
        this.illegalConverser = illegalConverserMessage;
        return this;
    }

    public @NotNull ConversationBuilder endListeners(@NotNull List<ConversationEndListener> conversationEndListeners) {
        this.endListeners = conversationEndListeners;
        return this;
    }

    public @NotNull ConversationBuilder addEndListener(@NotNull ConversationEndListener conversationEndListener) {
        this.endListeners.add(conversationEndListener);
        return this;
    }

    public @NotNull Conversation build(@NotNull Audience audience) {
        return build(getManager().get(audience));
    }

    public @NotNull Conversation build(@NotNull Converser converser) {
        ConversationContext conversationContext = new ConversationContext(converser, getInitialSessionData(), getManager());
        if(firstPrompt == null) throw new IllegalStateException("cant build conversation! \"firstPrompt\" is not set!");
        return new ImplConversation(firstPrompt,localEchoEnabled,prefix,initialSessionData,
                converserFilter,illegalConverser,endListeners, conversationContext);
    }


    public @NotNull ConversationManager getManager() {
        return manager;
    }
}
