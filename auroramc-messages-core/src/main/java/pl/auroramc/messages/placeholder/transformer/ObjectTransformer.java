package pl.auroramc.messages.placeholder.transformer;

public interface ObjectTransformer<T, R> {

  boolean supports(final Object value);

  R transform(final T value);

  Class<?> getType();
}
