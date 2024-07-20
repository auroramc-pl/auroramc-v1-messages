package pl.auroramc.messages.message.sanitizer;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import pl.auroramc.commons.tuplet.Pair;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;

public interface MessageSanitizer {

  static <T extends Audience> MessageSanitizer getMessageSanitizer(
      final PlaceholderResolver<T> placeholderResolver) {
    return new MessageSanitizerImpl<>(placeholderResolver);
  }

  Pair<MutableMessage, TagResolver[]> getSanitizedMessage(final MutableMessage message);
}
