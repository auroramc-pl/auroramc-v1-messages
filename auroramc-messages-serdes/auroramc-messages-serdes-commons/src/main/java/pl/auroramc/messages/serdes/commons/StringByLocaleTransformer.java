package pl.auroramc.messages.serdes.commons;

import java.util.Locale;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;
import pl.auroramc.messages.viewer.Viewer;

class StringByLocaleTransformer extends ObjectTransformer<Locale, String> {

  StringByLocaleTransformer() {
    super(Locale.class);
  }

  @Override
  public String transform(final Viewer viewer, final Locale value) {
    return value.getLanguage();
  }
}
