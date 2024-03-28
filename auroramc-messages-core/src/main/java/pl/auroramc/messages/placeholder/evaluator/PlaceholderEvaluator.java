package pl.auroramc.messages.placeholder.evaluator;

public interface PlaceholderEvaluator {

  static PlaceholderEvaluator getReflectivePlaceholderEvaluator() {
    return new ReflectivePlaceholderEvaluator();
  }

  Object evaluate(final Object object, final String path);

  Object evaluate(final Object object, final String path, final Class<?> type);

  Class<?> getReturnType(final Object object, final String path);
}
