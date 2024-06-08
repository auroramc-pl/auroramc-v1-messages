package pl.auroramc.messages.serdes.commons;

import static pl.auroramc.commons.format.temporal.TemporalFormatter.getFormattedTemporal;

import java.time.LocalDateTime;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;

class StringByLocalDateTimeTransformer extends ObjectTransformer<LocalDateTime, String> {

  StringByLocalDateTimeTransformer() {
    super(LocalDateTime.class);
  }

  @Override
  public String transform(final LocalDateTime value) {
    return getFormattedTemporal(value);
  }
}