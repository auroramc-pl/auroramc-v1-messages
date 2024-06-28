package pl.auroramc.messages.i18n;

import static java.util.Collections.emptySet;
import static pl.auroramc.commons.resource.ResourceUtils.unpackResources;
import static pl.auroramc.messages.i18n.MessageSourceUtils.getLocaleByFile;
import static pl.auroramc.messages.i18n.MessageSourceUtils.getMessageSource;

import eu.okaeri.configs.configurer.Configurer;
import java.io.File;
import java.util.Locale;
import java.util.function.Supplier;
import org.bukkit.command.CommandSender;
import pl.auroramc.messages.i18n.locale.LocaleProvider;

class BukkitMessageFacadeImpl extends MutableMessageService<CommandSender>
    implements BukkitMessageFacade {

  private final Supplier<Configurer> configurer;

  BukkitMessageFacadeImpl(
      final Supplier<Configurer> configurer,
      final Locale fallbackLocale,
      final LocaleProvider<CommandSender> localeProvider) {
    super(configurer, fallbackLocale, localeProvider);
    this.configurer = configurer;
  }

  @Override
  public BukkitMessageFacade registerResources(
      final Class<? extends MessageSource> messageSourceType,
      final File jarFile,
      final File dataPath,
      final String path,
      final String prefix,
      final String suffix) {
    unpackResources(jarFile, dataPath, path, prefix, suffix, emptySet())
        .forEach(resourceFile -> registerResource(messageSourceType, prefix, suffix, resourceFile));
    return this;
  }

  private void registerResource(
      final Class<? extends MessageSource> messageSourceType,
      final String prefix,
      final String suffix,
      final File resourceFile) {
    registerMessageSource(
        getLocaleByFile(prefix, suffix, resourceFile),
        getMessageSource(messageSourceType, configurer.get(), resourceFile));
  }
}
