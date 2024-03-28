package pl.auroramc.messages.message.compiler;

import static java.time.Duration.ofSeconds;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static pl.auroramc.messages.message.MutableMessage.LINE_DELIMITER;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.decoration.MessageDecoration;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;

class MessageCompilerImpl<T extends Audience> implements MessageCompiler<T> {

  private final PlaceholderResolver<T> placeholderResolver;
  private final Cache<String, CompiledMessage> compiledMessagesByTemplates;

  MessageCompilerImpl(final PlaceholderResolver<T> placeholderResolver) {
    this.placeholderResolver = placeholderResolver;
    this.compiledMessagesByTemplates =
        Caffeine.newBuilder().expireAfterAccess(ofSeconds(5)).build();
  }

  @Override
  public CompiledMessage compile(
      final T viewer, final MutableMessage message, final MessageDecoration... decorations) {
    final String resolvedMessage =
        placeholderResolver.resolve(viewer, message.getTemplate(), message.getContext());
    return compiledMessagesByTemplates.get(
        resolvedMessage, key -> compile0(resolvedMessage, decorations));
  }

  private CompiledMessage compile0(
      final String resolvedMessage, final MessageDecoration... decorations) {
    return new CompiledMessage(
        miniMessage()
            .deserialize(resolvedMessage)
            .decorations(
                stream(decorations)
                    .collect(toMap(MessageDecoration::decoration, MessageDecoration::state))));
  }

  @Override
  public CompiledMessage[] compileChildren(
      final T viewer, final MutableMessage message, final MessageDecoration... decorations) {
    return compileChildren(viewer, message, LINE_DELIMITER, decorations);
  }

  @Override
  public CompiledMessage[] compileChildren(
      final T viewer,
      final MutableMessage message,
      final String delimiter,
      final MessageDecoration... decorations) {
    return stream(message.children(delimiter))
        .map(child -> compile(viewer, child, decorations))
        .toArray(CompiledMessage[]::new);
  }
}
