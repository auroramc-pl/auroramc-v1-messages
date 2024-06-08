package pl.auroramc.messages.serdes.commons;

import static pl.auroramc.commons.format.temporal.TemporalFormatter.getFormattedTemporalShortly;

import java.time.LocalDate;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;

class StringByLocalDateTransformer extends ObjectTransformer<LocalDate, String> {

  StringByLocalDateTransformer() {
    super(LocalDate.class);
  }

  @Override
  public String transform(final LocalDate value) {
    return getFormattedTemporalShortly(value);
  }
}
