package pl.auroramc.messages.message.sanitizer;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import pl.auroramc.commons.tuplet.Pair;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;
import pl.auroramc.messages.viewer.Viewer;

public interface MessageSanitizer {

  static MessageSanitizer getMessageSanitizer(final PlaceholderResolver placeholderResolver) {
    return new MessageSanitizerImpl(placeholderResolver);
  }

  Pair<MutableMessage, TagResolver[]> getSanitizedMessage(
      final Viewer viewer, final MutableMessage message);
}
