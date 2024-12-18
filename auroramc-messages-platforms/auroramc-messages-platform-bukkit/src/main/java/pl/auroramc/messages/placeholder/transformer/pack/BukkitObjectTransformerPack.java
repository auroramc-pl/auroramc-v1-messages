package pl.auroramc.messages.placeholder.transformer.pack;

import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

public class BukkitObjectTransformerPack implements ObjectTransformerPack {

  @Override
  public void register(final ObjectTransformerRegistry transformerRegistry) {
    transformerRegistry.register(new BukkitComponentByTranslatableTransformer());
  }
}
