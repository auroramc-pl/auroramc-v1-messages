package pl.auroramc.messages.serdes.commons;

import java.time.Duration;
import pl.auroramc.commons.format.duration.DurationFormatter;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;

class StringByDurationTransformer extends ObjectTransformer<Duration, String> {

  private final DurationFormatter durationFormatter;

  StringByDurationTransformer(final DurationFormatter durationFormatter) {
    super(Duration.class);
    this.durationFormatter = durationFormatter;
  }

  @Override
  public String transform(final Duration value) {
    return durationFormatter.getFormattedDuration(value);
  }
}