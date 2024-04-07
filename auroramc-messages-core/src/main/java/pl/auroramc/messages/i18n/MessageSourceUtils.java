package pl.auroramc.messages.i18n;

import static java.nio.file.Files.exists;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.configurer.Configurer;
import eu.okaeri.configs.configurer.InMemoryConfigurer;
import java.io.File;
import java.util.Locale;

public final class MessageSourceUtils {

  private MessageSourceUtils() {}

  public static <S extends MessageSource> S getMessageSourceTemplate(
      final Class<S> messageSourceType) {
    final S template = ConfigManager.create(messageSourceType);
    template.withConfigurer(new InMemoryConfigurer());
    template.update();
    template.getDeclaration().getFields().forEach(field -> field.updateValue(field.getName()));
    return template;
  }

  public static <S extends MessageSource> S getMessageSource(
      final Class<S> messageSourceType, final Configurer configurer, final File bindFile) {
    return ConfigManager.create(
        messageSourceType,
        it -> {
          it.withConfigurer(configurer).withBindFile(bindFile);
          it.getDeclaration().getFields().forEach(field -> field.updateValue(null));
          if (exists(it.getBindFile())) {
            it.load();
          }
        });
  }

  public static Locale getLocaleByFile(final String prefix, final String suffix, final File file) {
    return Locale.forLanguageTag(
        file.getName().substring(prefix.length(), file.getName().length() - suffix.length()));
  }
}
