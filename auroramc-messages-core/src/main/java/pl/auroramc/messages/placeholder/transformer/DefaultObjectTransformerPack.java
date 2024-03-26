package pl.auroramc.messages.placeholder.transformer;

import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

public class DefaultObjectTransformerPack implements ObjectTransformerPack {

  @Override
  public void register(final ObjectTransformerRegistry transformerRegistry) {
    transformerRegistry.register(new StringToComponentTransformer());
  }
}
