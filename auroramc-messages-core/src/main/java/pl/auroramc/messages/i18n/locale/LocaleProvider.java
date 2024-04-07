package pl.auroramc.messages.i18n.locale;

import java.util.Locale;

@FunctionalInterface
public interface LocaleProvider<V> {

  Locale getLocale(final V viewer);
}
