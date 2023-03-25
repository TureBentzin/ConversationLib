package de.bentzin.conversationlib.manager;

import de.bentzin.conversationlib.ConversationBuilder;
import de.bentzin.conversationlib.Converser;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public class ConversationManager {

    private final @NotNull Map<Audience, Converser> converserMap;

    public ConversationManager(@NotNull Map<Audience, Converser> audienceConverserMap) {
        this.converserMap = audienceConverserMap;
    }

    public ConversationManager() {
        this.converserMap = new HashMap<>();
    }

    /**
     * Removes all entries out of the map where the key is not the same instance then the audience specified in the Converser!
     */
    protected void validateMap() {
        for (Map.Entry<Audience, Converser> entry : Collections.unmodifiableSet(converserMap.entrySet())) {
            if(entry.getKey() != entry.getValue().getTarget()) {
                converserMap.remove(entry.getKey());
            }
        }
    }

    public @NotNull Converser get(@NotNull Audience audience) {
        //step 1: map
        if(!converserMap.containsKey(audience)) {
            converserMap.put(audience, new Converser(audience));
        }
        return converserMap.get(audience);
    }

    /**
     * @param sender the sender
     * @param input the chat input
     * @return if the input was handled (if the message should NOT be sent)
     */
    public boolean handleInput(@NotNull Audience sender, @NotNull String input) {
        if(getConverserMap().containsKey(sender)) {
            getConverserMap().get(sender).getCurrentConversation().ifPresent(conversation -> conversation.acceptInput(input));
            return true;
        }
        return false;
    }

    /**
     *
     * @param d the object containing an audience and the string
     * @param audienceExtractor the extractor
     * @param stringExtractor the extractor
     * @return if the input was handled (if the message should NOT be sent)
     */
    public <D> boolean handleInputFromObject(@NotNull D d, @NotNull Function<D,? extends Audience> audienceExtractor,
                                             @NotNull Function<D,String> stringExtractor) {
        return handleInput(audienceExtractor.apply(d), stringExtractor.apply(d));
    }

    /**
     *
     * @param d the object containing an audience and the string
     * @param audienceExtractor the extractor
     * @param stringExtractor the extractor
     * @return if the input was handled (if the message should NOT be sent)
     */
    public <D> boolean handleInputFromObject(@NotNull D d, @NotNull Function<D,? extends Audience> audienceExtractor,
                                             @NotNull Function<D,String> stringExtractor, @NotNull Consumer<D> canceller) {
        boolean b = handleInput(audienceExtractor.apply(d), stringExtractor.apply(d));
        if(b) canceller.accept(d);
        return b;
    }

    public @NotNull @Unmodifiable Map<Audience, Converser> getConverserMap() {
        return Map.copyOf(converserMap);
    }

    public void removeAudience(@NotNull Audience audience) {
        converserMap.remove(audience);
    }

    public @NotNull ConversationBuilder newBuilder() {
        return new ConversationBuilder(this);
    }

    public @NotNull ConversationBuilder copyOf(@NotNull ConversationBuilder conversationBuilder) {
        if(!conversationBuilder.hasFirstPrompt()) throw new IllegalStateException("conversationBuilder has no firstPrompt!");
        return newBuilder().firstPrompt(conversationBuilder.getFirstPrompt())
                .converserFilter(conversationBuilder.getConverserFilter())
                .endListeners(conversationBuilder.getEndListeners())
                .illegalConverserMessage(conversationBuilder.getIllegalConverser())
                .localEchoEnabled(conversationBuilder.isLocalEchoEnabled())
                .prefix(conversationBuilder.getPrefix())
                .initialSessionData(conversationBuilder.getInitialSessionData());

    }
}
