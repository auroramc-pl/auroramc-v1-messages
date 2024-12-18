package pl.auroramc.messages.i18n;

import static java.nio.file.Files.exists;
import static java.util.Collections.unmodifiableSet;

import eu.okaeri.configs.configurer.Configurer;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import pl.auroramc.commons.tuplet.Pair;
import pl.auroramc.messages.viewer.Viewer;

abstract class MessageService<M> implements MessageFacade<M> {

  private final Supplier<Configurer> configurer;
  private final Locale fallbackLocale;
  private final Map<Locale, Map<String, M>> messagesByLocales;
  private final Set<Pair<Locale, MessageSource>> messageSourceByLocales;

  MessageService(final Supplier<Configurer> configurer, final Locale fallbackLocale) {
    this.configurer = configurer;
    this.fallbackLocale = fallbackLocale;
    this.messagesByLocales = new ConcurrentHashMap<>();
    this.messageSourceByLocales = new HashSet<>();
  }

  @Override
  public void refresh() {
    messagesByLocales.clear();
    for (final Pair<Locale, MessageSource> messageSourceByLocale : messageSourceByLocales) {
      final MessageSource messageSource = messageSourceByLocale.b();
      if (exists(messageSource.getBindFile())) {
        messageSource.load();
      }
      registerMessageSource(messageSourceByLocale.a(), messageSource);
    }
  }

  @Override
  public <S extends MessageSource> void registerMessageSource(
      final Locale locale, final S messageSource) {
    final Map<String, Object> valuesByKeys = messageSource.asMap(configurer.get(), true);
    final Map<String, M> messagesByKeys = new LinkedHashMap<>();
    for (final Entry<String, Object> entry : valuesByKeys.entrySet()) {
      messagesByKeys.put(entry.getKey(), getMessageToPersist((String) entry.getValue()));
    }
    messageSourceByLocales.add(new Pair<>(locale, messageSource));
    messagesByLocales.computeIfAbsent(locale, key -> new LinkedHashMap<>()).putAll(messagesByKeys);
  }

  @Override
  public M getMessage(final Locale locale, final String key) {
    if (messagesByLocales.containsKey(locale)) {
      return messagesByLocales.get(locale).get(key);
    }

    final Locale strippedLocale = Locale.of(locale.getLanguage());
    if (messagesByLocales.containsKey(strippedLocale)) {
      return messagesByLocales.get(strippedLocale).get(key);
    }

    if (messagesByLocales.containsKey(fallbackLocale)) {
      return messagesByLocales.get(fallbackLocale).get(key);
    }

    return getMessageToPersist(key);
  }

  @Override
  public M getMessage(final Viewer viewer, final String key) {
    return getMessage(viewer.getLocale(), key);
  }

  @Override
  public Set<Locale> getAvailableLocales() {
    return unmodifiableSet(messagesByLocales.keySet());
  }
}
