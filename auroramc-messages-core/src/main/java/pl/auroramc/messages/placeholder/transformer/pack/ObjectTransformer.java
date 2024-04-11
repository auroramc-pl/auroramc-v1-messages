package pl.auroramc.messages.placeholder.transformer.pack;

public interface ObjectTransformer<T, R> {

  R transform(final T value);

  Class<?> type();
}
