package pl.auroramc.messages.serdes.commons;

import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

public class CommonsObjectTransformerPack implements ObjectTransformerPack {

  @Override
  public void register(final ObjectTransformerRegistry transformerRegistry) {
    transformerRegistry.register(new StringByBigDecimalTransformer());
    transformerRegistry.register(new StringByDurationTransformer());
    transformerRegistry.register(new StringByInstantTransformer());
    transformerRegistry.register(new StringByLocalDateTimeTransformer());
    transformerRegistry.register(new StringByLocalDateTransformer());
    transformerRegistry.register(new StringByLocaleTransformer());
  }
}
