package pl.auroramc.messages.message.compiler;

import static java.time.Duration.ofSeconds;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static pl.auroramc.messages.message.MutableMessage.LINE_DELIMITER;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.decoration.MessageDecoration;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;

class MessageCompilerImpl implements MessageCompiler {

  private final PlaceholderResolver placeholderResolver;
  private final Cache<String, CompiledMessage> compiledMessagesByTemplates;

  MessageCompilerImpl(final PlaceholderResolver placeholderResolver) {
    this.placeholderResolver = placeholderResolver;
    this.compiledMessagesByTemplates =
        Caffeine.newBuilder().expireAfterAccess(ofSeconds(10)).build();
  }

  @Override
  public CompiledMessage compile(
      final MutableMessage message, final MessageDecoration... decorations) {
    return compiledMessagesByTemplates.get(
        message.getTemplate(), key -> compile0(message, decorations));
  }

  private CompiledMessage compile0(
      final MutableMessage message, final MessageDecoration... decorations) {
    return new CompiledMessage(
        miniMessage()
            .deserialize(
                placeholderResolver.resolve(message.getTemplate(), message.getPlaceholderContext()))
            .decorations(
                stream(decorations)
                    .collect(toMap(MessageDecoration::decoration, MessageDecoration::state))));
  }

  @Override
  public CompiledMessage[] compileChildren(
      final MutableMessage message, final MessageDecoration... decorations) {
    return compileChildren(message, LINE_DELIMITER, decorations);
  }

  @Override
  public CompiledMessage[] compileChildren(
      final MutableMessage message,
      final String delimiter,
      final MessageDecoration... decorations) {
    return stream(message.children(delimiter))
        .map(child -> compile(child, decorations))
        .toArray(CompiledMessage[]::new);
  }
}
