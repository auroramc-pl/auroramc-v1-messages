package pl.auroramc.messages.placeholder.transformer;

import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

public interface ObjectTransformerPack {

  void register(final ObjectTransformerRegistry transformerRegistry);
}
