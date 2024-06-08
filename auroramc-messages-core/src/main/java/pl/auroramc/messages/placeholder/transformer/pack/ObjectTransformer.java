package pl.auroramc.messages.placeholder.transformer.pack;

public abstract class ObjectTransformer<T, R> {

  private final Class<?> transformatableType;

  protected ObjectTransformer(final Class<?> transformatableType) {
    this.transformatableType = transformatableType;
  }

  public abstract R transform(final T value);

  public Class<?> getTransformatableType() {
    return transformatableType;
  }
}
