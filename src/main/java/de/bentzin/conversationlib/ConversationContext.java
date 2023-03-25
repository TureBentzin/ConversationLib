package de.bentzin.conversationlib;

import de.bentzin.conversationlib.manager.ConversationManager;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author Ture Bentzin
 * 25.03.2023
 */
public record ConversationContext(@NotNull Converser target, @NotNull Map<Object, Object> sessionData,
                                  @NotNull ConversationManager associatedManager) {

    public <K, V> @NotNull V getData(@NotNull K key) {
        return (V) sessionData().get(key);
    }

    public <K, V> void setData(@NotNull K key, @NotNull V value) {
        sessionData().put(key, value);
    }
}
