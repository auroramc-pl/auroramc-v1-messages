package pl.auroramc.messages.placeholder.transformer.pack;

public interface ObjectTransformer<T, R> {

  default boolean supports(final Object value) {
    return value.getClass().isAssignableFrom(getType());
  }

  R transform(final T value);

  Class<?> getType();
}
