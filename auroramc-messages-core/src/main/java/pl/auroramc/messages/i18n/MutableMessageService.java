package pl.auroramc.messages.i18n;

import eu.okaeri.configs.configurer.Configurer;
import java.util.Locale;
import java.util.function.Supplier;
import pl.auroramc.messages.i18n.locale.LocaleProvider;
import pl.auroramc.messages.message.MutableMessage;

class MutableMessageService<V> extends MessageService<MutableMessage, V> {

  MutableMessageService(
      final Supplier<Configurer> configurer,
      final Locale fallbackLocale,
      final LocaleProvider<V> localeProvider) {
    super(configurer, fallbackLocale, localeProvider);
  }

  @Override
  public MutableMessage getMessageToPersist(final String message) {
    return MutableMessage.of(message);
  }
}
