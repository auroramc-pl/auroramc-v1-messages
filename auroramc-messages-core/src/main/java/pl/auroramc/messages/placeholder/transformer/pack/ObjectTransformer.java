package pl.auroramc.messages.placeholder.transformer.pack;

public abstract class ObjectTransformer<T, R> {

  private final Class<?> transformationType;

  protected ObjectTransformer(final Class<?> transformationType) {
    this.transformationType = transformationType;
  }

  public abstract R transform(final T value);

  public Class<?> getTransformationType() {
    return transformationType;
  }
}
