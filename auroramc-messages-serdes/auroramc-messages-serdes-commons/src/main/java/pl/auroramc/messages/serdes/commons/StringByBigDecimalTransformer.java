package pl.auroramc.messages.serdes.commons;

import static pl.auroramc.commons.format.decimal.DecimalFormatter.getFormattedDecimal;

import java.math.BigDecimal;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;
import pl.auroramc.messages.viewer.Viewer;

class StringByBigDecimalTransformer extends ObjectTransformer<BigDecimal, String> {

  StringByBigDecimalTransformer() {
    super(BigDecimal.class);
  }

  @Override
  public String transform(final Viewer viewer, final BigDecimal value) {
    return getFormattedDecimal(value);
  }
}
