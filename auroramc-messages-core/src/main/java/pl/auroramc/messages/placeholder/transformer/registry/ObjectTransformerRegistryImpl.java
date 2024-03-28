package pl.auroramc.messages.placeholder.transformer.registry;

import java.util.HashMap;
import java.util.Map;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;

class ObjectTransformerRegistryImpl implements ObjectTransformerRegistry {

  private final Map<Class<?>, ObjectTransformer<?, ?>> transformersByTypes;
  private final Map<Class<?>, ObjectTransformer<?, ?>> transformersByTypesCached;

  ObjectTransformerRegistryImpl() {
    this.transformersByTypes = new HashMap<>();
    this.transformersByTypesCached = new HashMap<>();
  }

  @Override
  public <T, R> ObjectTransformer<T, R> getTransformer(final Class<?> type) {
    if (transformersByTypes.containsKey(type)) {
      // noinspection unchecked
      return (ObjectTransformer<T, R>) transformersByTypes.get(type);
    }

    // noinspection unchecked
    return (ObjectTransformer<T, R>)
        transformersByTypesCached.computeIfAbsent(type, key -> getDeepTransformer(type));
  }

  private <T, R> ObjectTransformer<T, R> getDeepTransformer(final Class<?> type) {
    for (final Map.Entry<Class<?>, ObjectTransformer<?, ?>> entry :
        transformersByTypes.entrySet()) {
      if (entry.getKey().isAssignableFrom(type)) {
        // noinspection unchecked
        return (ObjectTransformer<T, R>) entry.getValue();
      }
    }
    return null;
  }

  @Override
  public boolean hasTransformer(final Class<?> type) {
    return transformersByTypes.containsKey(type);
  }

  @Override
  public void register(final ObjectTransformer<?, ?> transformer) {
    transformersByTypes.put(transformer.getType(), transformer);
  }

  @Override
  public void register(final ObjectTransformerPack... transformerPacks) {
    for (final ObjectTransformerPack transformerPack : transformerPacks) {
      transformerPack.register(this);
    }
  }
}
