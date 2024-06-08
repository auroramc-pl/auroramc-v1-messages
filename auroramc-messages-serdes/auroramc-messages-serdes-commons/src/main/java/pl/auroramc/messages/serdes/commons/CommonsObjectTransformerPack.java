package pl.auroramc.messages.serdes.commons;

import static pl.auroramc.commons.format.duration.settings.DurationFormatterSettingsUtils.getDefaultFormatterSettings;
import static pl.auroramc.commons.i18n.Locales.POLISH;
import static pl.auroramc.commons.i18n.plural.Pluralizers.getPluralizer;

import pl.auroramc.commons.format.duration.DurationFormatter;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

public class CommonsObjectTransformerPack implements ObjectTransformerPack {

  private final DurationFormatter durationFormatter;

  public CommonsObjectTransformerPack(final DurationFormatter durationFormatter) {
    this.durationFormatter = durationFormatter;
  }

  public CommonsObjectTransformerPack() {
    this(new DurationFormatter(getPluralizer(POLISH), getDefaultFormatterSettings()));
  }

  @Override
  public void register(final ObjectTransformerRegistry transformerRegistry) {
    transformerRegistry.register(new StringByBigDecimalTransformer());
    transformerRegistry.register(new StringByDurationTransformer(durationFormatter));
    transformerRegistry.register(new StringByInstantTransformer());
    transformerRegistry.register(new StringByLocalDateTimeTransformer());
    transformerRegistry.register(new StringByLocalDateTransformer());
  }
}