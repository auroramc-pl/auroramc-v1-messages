package pl.auroramc.messages.i18n;

import com.velocitypowered.api.command.CommandSource;
import eu.okaeri.configs.configurer.Configurer;
import java.io.File;
import java.util.Locale;
import java.util.function.Supplier;
import pl.auroramc.messages.i18n.locale.LocaleProvider;
import pl.auroramc.messages.i18n.locale.VelocityLocaleProvider;
import pl.auroramc.messages.message.MutableMessage;

public interface VelocityMessageFacade extends MessageFacade<MutableMessage, CommandSource> {

  static VelocityMessageFacade getVelocityMessageFacade(
      final Supplier<Configurer> configurer, final Locale fallbackLocale) {
    return getVelocityMessageFacade(
        configurer, fallbackLocale, new VelocityLocaleProvider(fallbackLocale));
  }

  static VelocityMessageFacade getVelocityMessageFacade(
      final Supplier<Configurer> configurer,
      final Locale fallbackLocale,
      final LocaleProvider<CommandSource> localeProvider) {
    return new VelocityMessageFacadeImpl(configurer, fallbackLocale, localeProvider);
  }

  VelocityMessageFacade registerResources(
      final Class<? extends MessageSource> messageSourceType,
      final File jarFile,
      final File dataPath,
      final String path,
      final String prefix,
      final String suffix);
}
