package pl.auroramc.messages.placeholder.transformer.pack.standard;

import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

public class StandardObjectTransformerPack implements ObjectTransformerPack {

  @Override
  public void register(final ObjectTransformerRegistry transformerRegistry) {
    transformerRegistry.register(new StringToComponentTransformer());
  }
}
