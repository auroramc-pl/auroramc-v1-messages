package pl.auroramc.messages.i18n;

import eu.okaeri.configs.configurer.Configurer;
import java.io.File;
import java.util.Locale;
import java.util.function.Supplier;
import org.bukkit.command.CommandSender;
import pl.auroramc.messages.i18n.locale.LocaleProvider;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.i18n.locale.BukkitLocaleProvider;

public interface BukkitMessageFacade extends MessageFacade<MutableMessage, CommandSender> {

  static BukkitMessageFacade getBukkitMessageFacade(
      final Supplier<Configurer> configurer, final Locale fallbackLocale) {
    return getBukkitMessageFacade(
        configurer, fallbackLocale, new BukkitLocaleProvider(fallbackLocale));
  }

  static BukkitMessageFacade getBukkitMessageFacade(
      final Supplier<Configurer> configurer,
      final Locale fallbackLocale,
      final LocaleProvider<CommandSender> localeProvider) {
    return new BukkitMessageFacadeImpl(configurer, fallbackLocale, localeProvider);
  }

  BukkitMessageFacade registerResources(
      final Class<? extends MessageSource> messageSourceType,
      final File jarFile,
      final File dataPath,
      final String path,
      final String prefix,
      final String suffix);
}
