package pl.auroramc.messages.message.compiler;

import static java.time.Duration.ofSeconds;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static net.kyori.adventure.audience.Audience.audience;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static pl.auroramc.messages.message.MutableMessage.LINE_DELIMITER;
import static pl.auroramc.messages.message.sanitizer.MessageSanitizer.getMessageSanitizer;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.Executor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import pl.auroramc.commons.tuplet.Pair;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.decoration.MessageDecoration;
import pl.auroramc.messages.message.group.MutableMessageGroup;
import pl.auroramc.messages.message.sanitizer.MessageSanitizer;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;

class MessageCompilerImpl<T extends Audience> implements MessageCompiler<T> {

  private final MessageSanitizer messageSanitizer;
  private final PlaceholderResolver<T> placeholderResolver;
  private final Cache<MutableMessage, CompiledMessage> compiledMessagesByTemplates;

  MessageCompilerImpl(final Executor executor, final PlaceholderResolver<T> placeholderResolver) {
    this.messageSanitizer = getMessageSanitizer(placeholderResolver);
    this.placeholderResolver = placeholderResolver;
    this.compiledMessagesByTemplates =
        Caffeine.newBuilder().executor(executor).expireAfterAccess(ofSeconds(5)).build();
  }

  @Override
  public CompiledMessage compile(
      final T viewer, final MutableMessage message, final MessageDecoration... decorations) {
    final MutableMessage resolvedMessage = placeholderResolver.resolve(viewer, message);
    return compiledMessagesByTemplates.get(
        resolvedMessage, key -> compile0(resolvedMessage, decorations));
  }

  private CompiledMessage compile0(
      final MutableMessage message, final MessageDecoration... decorations) {
    final Pair<MutableMessage, TagResolver[]> sanitizedMessage =
        messageSanitizer.getSanitizedMessage(message);
    return new CompiledMessage(
        miniMessage()
            .deserialize(sanitizedMessage.a().getTemplate(), sanitizedMessage.b())
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

  @Override
  public CompiledMessageGroup compileGroup(
      final MutableMessageGroup messageGroup, final MessageDecoration... decorations) {
    return new CompiledMessageGroup(
        messageGroup.messagesByReceivers().entrySet().stream()
            .collect(
                toUnmodifiableMap(
                    entry -> compile(null, entry.getKey(), decorations),
                    entry -> audience(entry.getValue()))));
  }

  @Override
  public void register(final ObjectTransformerPack... transformerPacks) {
    placeholderResolver.register(transformerPacks);
  }
}
