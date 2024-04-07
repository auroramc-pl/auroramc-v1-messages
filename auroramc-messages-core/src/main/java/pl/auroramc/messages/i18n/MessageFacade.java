package pl.auroramc.messages.i18n;

import java.util.Locale;
import java.util.Set;

public interface MessageFacade<M, V> {

  <S extends MessageSource> void registerMessageSource(
      final Locale locale, final S messageSource);

  M getMessage(final Locale locale, final String key);

  M getMessage(final V viewer, final String key);

  M getMessageToPersist(final String message);

  Set<Locale> getAvailableLocales();
}
