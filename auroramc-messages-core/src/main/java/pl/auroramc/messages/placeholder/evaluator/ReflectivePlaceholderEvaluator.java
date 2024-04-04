package pl.auroramc.messages.placeholder.evaluator;

import static java.lang.invoke.MethodHandles.privateLookupIn;
import static java.lang.reflect.Modifier.isPublic;
import static pl.auroramc.messages.placeholder.evaluator.ReflectivePlaceholderEvaluatorUtils.resolveTokens;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class ReflectivePlaceholderEvaluator implements PlaceholderEvaluator {

  private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
  private static final Map<Class<?>, MethodType> METHOD_TYPE_CACHE = new HashMap<>();
  private static final Map<Class<?>, MethodHandles.Lookup> LOOKUP_CACHE = new HashMap<>();
  private static final Map<PlaceholderCompositeKey, Class<?>> RETURN_TYPE_CACHE = new HashMap<>();

  ReflectivePlaceholderEvaluator() {}

  @Override
  public Object evaluate(final Object object, final String path) {
    return evaluate(object, path, getReturnType(object, path));
  }

  @Override
  public Object evaluate(final Object object, final String path, final Class<?> type) {
    try {
      return getMethodHandle(object, path, type).invoke(object);
    } catch (final Throwable exception) {
      throw new PlaceholderEvaluationException(
          "Could not evaluate placeholders, because of unexpected exception.", exception);
    }
  }

  @Override
  public Class<?> getReturnType(final Object object, final String path) {
    return RETURN_TYPE_CACHE.computeIfAbsent(
        new PlaceholderCompositeKey(object.getClass(), path),
        key -> getReturnType0(object, resolveTokens(path)));
  }

  private MethodHandle getMethodHandle(
      final Object object, final String path, final Class<?> type) {
    final MethodType methodType = METHOD_TYPE_CACHE.computeIfAbsent(type, MethodType::methodType);
    try {
      final Class<?> clazz = object.getClass();
      if (LOOKUP_CACHE.containsKey(clazz)) {
        return LOOKUP_CACHE.get(clazz).findVirtual(clazz, resolveTokens(path), methodType);
      }

      final MethodHandles.Lookup lookup =
          isPublic(clazz.getModifiers()) ? LOOKUP : privateLookupIn(clazz, LOOKUP);
      LOOKUP_CACHE.put(clazz, lookup);
      return lookup.findVirtual(clazz, resolveTokens(path), methodType);
    } catch (final NoSuchMethodException | IllegalAccessException exception) {
      throw new PlaceholderEvaluationException(
          "Could not evaluate placeholders, because of failed retrieval of method handle.",
          exception);
    }
  }

  private Class<?> getReturnType0(final Object object, final String path) {
    final Class<?> clazz = object.getClass();
    try {
      final Method method = clazz.getMethod(path);
      if (method.getReturnType() == void.class) {
        throw new PlaceholderEvaluationException(
            "Could not evaluate placeholders, because the method returns void.");
      }

      return method.getReturnType();
    } catch (final NoSuchMethodException exception) {
      throw new PlaceholderEvaluationException(
          "Could not evaluate placeholders, because of unexpected exception.", exception);
    }
  }

  private record PlaceholderCompositeKey(Class<?> type, String path) {}
}
