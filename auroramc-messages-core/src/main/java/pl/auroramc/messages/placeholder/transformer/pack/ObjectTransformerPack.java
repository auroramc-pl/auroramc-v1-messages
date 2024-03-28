package pl.auroramc.messages.placeholder.transformer.pack;

import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

@FunctionalInterface
public interface ObjectTransformerPack {

  void register(final ObjectTransformerRegistry transformerRegistry);
}
