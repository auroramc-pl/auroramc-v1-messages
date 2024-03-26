package pl.auroramc.messages.placeholder.transformer.pack;

public interface ObjectTransformer<T, R> {

  boolean supports(final Object value);

  R transform(final T value);

  Class<?> getType();
}
