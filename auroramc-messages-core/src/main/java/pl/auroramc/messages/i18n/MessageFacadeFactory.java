package pl.auroramc.messages.i18n;

import eu.okaeri.configs.configurer.Configurer;
import java.util.Locale;
import java.util.function.Supplier;
import pl.auroramc.messages.i18n.locale.LocaleProvider;
import pl.auroramc.messages.message.MutableMessage;

public final class MessageFacadeFactory {

  private MessageFacadeFactory() {}

  public static <V> MessageFacade<MutableMessage, V> getMessageFacade(
      final Supplier<Configurer> configurer,
      final Locale fallbackLocale,
      final LocaleProvider<V> localeProvider) {
    return new MutableMessageService<>(configurer, fallbackLocale, localeProvider);
  }
}
