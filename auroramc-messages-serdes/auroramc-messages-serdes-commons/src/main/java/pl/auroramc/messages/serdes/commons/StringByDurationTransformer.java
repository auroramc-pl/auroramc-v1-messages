package pl.auroramc.messages.serdes.commons;

import static pl.auroramc.commons.format.duration.DurationFormatter.getFormattedDuration;
import static pl.auroramc.commons.i18n.plural.Pluralizers.getPluralizer;
import static pl.auroramc.messages.serdes.commons.StringByDurationTransformerUtils.getDurationFormatterVocabularyByLocale;

import java.time.Duration;
import java.util.Locale;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;
import pl.auroramc.messages.viewer.Viewer;

class StringByDurationTransformer extends ObjectTransformer<Duration, String> {

  StringByDurationTransformer() {
    super(Duration.class);
  }

  @Override
  public String transform(final Viewer viewer, final Duration value) {
    final Locale locale = viewer.getLocale();
    return getFormattedDuration(
        getDurationFormatterVocabularyByLocale(locale), getPluralizer(locale), value);
  }
}
