package pl.auroramc.messages.i18n;

import eu.okaeri.configs.configurer.Configurer;
import java.io.File;
import java.util.Locale;
import java.util.function.Supplier;
import pl.auroramc.messages.message.MutableMessage;

public interface VelocityMessageFacade extends MessageFacade<MutableMessage> {

  static VelocityMessageFacade getVelocityMessageFacade(
      final Supplier<Configurer> configurer, final Locale fallbackLocale) {
    return new VelocityMessageFacadeImpl(configurer, fallbackLocale);
  }

  VelocityMessageFacade registerResources(
      final Class<? extends MessageSource> messageSourceType,
      final File jarFile,
      final File dataPath,
      final String path,
      final String prefix,
      final String suffix);
}
