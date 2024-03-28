package pl.auroramc.messages.placeholder.transformer.registry;

import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;

public interface ObjectTransformerRegistry {

  static ObjectTransformerRegistry getObjectTransformerRegistry(
      final ObjectTransformerPack... transformerPacks) {
    final ObjectTransformerRegistry transformerRegistry = new ObjectTransformerRegistryImpl();
    for (final ObjectTransformerPack transformerPack : transformerPacks) {
      transformerPack.register(transformerRegistry);
    }
    return transformerRegistry;
  }

  <T, R> ObjectTransformer<T, R> getTransformer(final Class<?> type);

  boolean hasTransformer(final Class<?> type);

  void register(final ObjectTransformer<?, ?> transformer);

  void register(final ObjectTransformerPack... transformerPacks);
}
