package pl.auroramc.messages.serdes.commons;

import static pl.auroramc.commons.format.temporal.TemporalFormatter.getFormattedTemporal;

import java.time.Instant;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;
import pl.auroramc.messages.viewer.Viewer;

class StringByInstantTransformer extends ObjectTransformer<Instant, String> {

  StringByInstantTransformer() {
    super(Instant.class);
  }

  @Override
  public String transform(final Viewer viewer, final Instant value) {
    return getFormattedTemporal(value);
  }
}
