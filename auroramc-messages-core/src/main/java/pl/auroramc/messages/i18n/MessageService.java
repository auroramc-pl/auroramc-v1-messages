package pl.auroramc.messages.i18n;

import static java.util.Collections.unmodifiableSet;

import eu.okaeri.configs.configurer.Configurer;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import pl.auroramc.messages.i18n.locale.LocaleProvider;

abstract class MessageService<M, V> implements MessageFacade<M, V> {

  private final Supplier<Configurer> configurer;
  private final Locale fallbackLocale;
  private final LocaleProvider<V> localeProvider;
  private final Map<Locale, Map<String, M>> messagesByLocales;

  MessageService(
      final Supplier<Configurer> configurer,
      final Locale fallbackLocale,
      final LocaleProvider<V> localeProvider) {
    this.configurer = configurer;
    this.fallbackLocale = fallbackLocale;
    this.localeProvider = localeProvider;
    this.messagesByLocales = new ConcurrentHashMap<>();
  }

  @Override
  public <S extends MessageSource> void registerMessageSource(
      final Locale locale, final S messageSource) {
    final Map<String, Object> valuesByKeys = messageSource.asMap(configurer.get(), true);
    final Map<String, M> messagesByKeys = new LinkedHashMap<>();
    for (final Entry<String, Object> entry : valuesByKeys.entrySet()) {
      messagesByKeys.put(entry.getKey(), getMessageToPersist((String) entry.getValue()));
    }
    messagesByLocales.put(locale, messagesByKeys);
  }

  @Override
  public M getMessage(final Locale locale, final String key) {
    if (messagesByLocales.containsKey(locale)) {
      return messagesByLocales.get(locale).get(key);
    }

    final Locale strippedLocale = new Locale(locale.getLanguage());
    if (messagesByLocales.containsKey(strippedLocale)) {
      return messagesByLocales.get(strippedLocale).get(key);
    }

    if (messagesByLocales.containsKey(fallbackLocale)) {
      return messagesByLocales.get(fallbackLocale).get(key);
    }

    return getMessageToPersist(key);
  }

  @Override
  public M getMessage(final V viewer, final String key) {
    return getMessage(localeProvider.getLocale(viewer), key);
  }

  @Override
  public Set<Locale> getAvailableLocales() {
    return unmodifiableSet(messagesByLocales.keySet());
  }
}
