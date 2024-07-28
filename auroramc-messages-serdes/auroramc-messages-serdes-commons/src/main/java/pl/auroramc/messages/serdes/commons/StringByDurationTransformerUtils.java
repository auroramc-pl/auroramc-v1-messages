package pl.auroramc.messages.serdes.commons;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static java.util.Locale.ENGLISH;
import static pl.auroramc.commons.i18n.Locales.POLISH;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import pl.auroramc.commons.format.duration.DurationFormatterVocabulary;
import pl.auroramc.commons.format.duration.DurationFormatterVocabularyBuilder;
import pl.auroramc.commons.i18n.plural.variety.VarietiesByCases;
import pl.auroramc.commons.i18n.plural.variety.VarietiesByCasesBuilder;
import pl.auroramc.commons.tuplet.Pair;
import pl.auroramc.commons.tuplet.Triple;

final class StringByDurationTransformerUtils {

  private static final Map<Locale, DurationFormatterVocabulary> FORMATTER_VOCABULARIES_BY_LOCALE =
      new HashMap<>();

  static {
    FORMATTER_VOCABULARIES_BY_LOCALE.put(POLISH, getDurationFormatterVocabularyInPolish());
    FORMATTER_VOCABULARIES_BY_LOCALE.put(ENGLISH, getDurationFormatterVocabularyInEnglish());
  }

  private StringByDurationTransformerUtils() {}

  static DurationFormatterVocabulary getDurationFormatterVocabularyByLocale(final Locale locale) {
    final Locale strippedLocale = Locale.of(locale.getLanguage());
    return FORMATTER_VOCABULARIES_BY_LOCALE.getOrDefault(
        strippedLocale, FORMATTER_VOCABULARIES_BY_LOCALE.get(ENGLISH));
  }

  private static DurationFormatterVocabulary getDurationFormatterVocabularyInPolish() {
    return getDurationFormatterVocabulary(
        " i ",
        " oraz ",
        Map.of(
            YEARS, new Triple<>("rok", "lata", "lat"),
            MONTHS, new Triple<>("miesiąc", "miesiące", "miesięcy"),
            WEEKS, new Triple<>("tydzień", "tygodnie", "tygodni"),
            DAYS, new Triple<>("dzień", "dni", "dni"),
            HOURS, new Triple<>("godzina", "godziny", "godzin"),
            MINUTES, new Triple<>("minuta", "minuty", "minut"),
            SECONDS, new Triple<>("sekunda", "sekundy", "sekund"),
            MILLIS, new Triple<>("milisekunda", "milisekundy", "milisekund")),
        unitForm -> VarietiesByCasesBuilder.newBuilder().withPluralForm(unitForm).build());
  }

  private static DurationFormatterVocabulary getDurationFormatterVocabularyInEnglish() {
    return getDurationFormatterVocabulary(
        ", ",
        " and ",
        Map.of(
            YEARS, new Pair<>("year", "years"),
            MONTHS, new Pair<>("month", "months"),
            WEEKS, new Pair<>("week", "weeks"),
            DAYS, new Pair<>("day", "days"),
            HOURS, new Pair<>("hour", "hours"),
            MINUTES, new Pair<>("minute", "minutes"),
            SECONDS, new Pair<>("second", "seconds"),
            MILLIS, new Pair<>("millisecond", "milliseconds")),
        unitForm -> VarietiesByCasesBuilder.newBuilder().withPluralForm(unitForm).build());
  }

  private static <T> DurationFormatterVocabulary getDurationFormatterVocabulary(
      final String aggregatingPhrase,
      final String aggregatingPhraseEnclosing,
      final Map<ChronoUnit, T> unitForms,
      final Function<T, VarietiesByCases> varietiesByCases) {
    final DurationFormatterVocabularyBuilder formatterVocabularyBuilder =
        DurationFormatterVocabularyBuilder.newBuilder()
            .withAggregatingPhrase(aggregatingPhrase)
            .withAggregatingPhraseEnclosing(aggregatingPhraseEnclosing);
    unitForms.forEach(
        (unit, unitForm) ->
            formatterVocabularyBuilder.withUnitForm(unit, varietiesByCases.apply(unitForm)));
    return formatterVocabularyBuilder.build();
  }
}
