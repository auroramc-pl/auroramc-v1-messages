package pl.auroramc.messages.i18n;

import eu.okaeri.configs.configurer.Configurer;
import java.util.Locale;
import java.util.function.Supplier;
import pl.auroramc.messages.message.MutableMessage;

class MutableMessageService extends MessageService<MutableMessage> {

  MutableMessageService(final Supplier<Configurer> configurer, final Locale fallbackLocale) {
    super(configurer, fallbackLocale);
  }

  @Override
  public MutableMessage getMessageToPersist(final String message) {
    return MutableMessage.of(message);
  }
}
