package pl.auroramc.messages.message.sanitizer;

import static java.util.Locale.ROOT;
import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component;
import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed;
import static pl.auroramc.commons.format.StringUtils.BLANK;
import static pl.auroramc.messages.placeholder.evaluator.ReflectivePlaceholderEvaluatorUtils.PATH_TOKENS;
import static pl.auroramc.messages.placeholder.scanner.PlaceholderScannerUtils.PATH_CHILDREN_DELIMITER;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import pl.auroramc.commons.tuplet.Pair;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;
import pl.auroramc.messages.viewer.Viewer;

class MessageSanitizerImpl implements MessageSanitizer {

  private static final int UNKNOWN_INDEX = -1;
  private final PlaceholderResolver placeholderResolver;
  private final Map<String, String> normalizedKeyByRawKey;

  MessageSanitizerImpl(final PlaceholderResolver placeholderResolver) {
    this.placeholderResolver = placeholderResolver;
    this.normalizedKeyByRawKey = new ConcurrentHashMap<>();
  }

  @Override
  public Pair<MutableMessage, TagResolver[]> getSanitizedMessage(
      final Viewer viewer, MutableMessage message) {
    String template = message.getTemplate();

    final Set<TagResolver> placeholders = new HashSet<>();
    for (final Entry<String, Object> placeholderKeyToValue :
        message.getProperty().getValuesByPaths().entrySet()) {
      final String rawKey = placeholderKeyToValue.getKey();
      final String targetKey = getPlaceholderKey(rawKey);
      if (!rawKey.equals(targetKey)) {
        template = template.replace(rawKey, targetKey);
        template =
            template.replace(targetKey + PATH_CHILDREN_DELIMITER, rawKey + PATH_CHILDREN_DELIMITER);
      }

      final Object transformedValue =
          placeholderResolver.transform(viewer, placeholderKeyToValue.getValue());
      placeholders.add(getSanitizedPlaceholder(targetKey, transformedValue));
    }

    return new Pair<>(
        MutableMessage.of(template, message.getProperty()),
        placeholders.toArray(TagResolver[]::new));
  }

  private String getPlaceholderKey(final String key) {
    return normalizedKeyByRawKey.computeIfAbsent(key, this::getPlaceholderKey0);
  }

  private String getPlaceholderKey0(String key) {
    for (final char token : PATH_TOKENS.keySet()) {
      key = key.replace(String.valueOf(token), BLANK);
    }

    final int lastChildIndex = key.lastIndexOf(PATH_CHILDREN_DELIMITER);
    if (lastChildIndex != UNKNOWN_INDEX) {
      key = key.substring(lastChildIndex + 1);
    }

    return key.toLowerCase(ROOT);
  }

  private TagResolver getSanitizedPlaceholder(final String key, final Object transformedValue) {
    if (transformedValue instanceof Component component) {
      return component(key, component);
    }

    return unparsed(key, transformedValue.toString());
  }
}
