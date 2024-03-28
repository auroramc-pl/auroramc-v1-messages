package pl.auroramc.messages.placeholder.resolver;

import static java.lang.Math.max;

import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

final class PlaceholderResolverUtils {

  private static final String KYORI_PACKAGE_NAME = "net.kyori.adventure.text";
  private static final String PLACEHOLDER_KEY_TOKEN_INITIAL = "{";
  private static final String PLACEHOLDER_KEY_TOKEN_ENCLOSE = "}";

  private PlaceholderResolverUtils() {}

  static String getPlaceholderKey(final String path) {
    return PLACEHOLDER_KEY_TOKEN_INITIAL + path + PLACEHOLDER_KEY_TOKEN_ENCLOSE;
  }

  static Class<?> getParentType(
      final ObjectTransformerRegistry objectTransformerRegistry, final Object value) {
    if (value == null) {
      return null;
    }

    if (requiresDeepSearch(value)) {
      Class<?> currentType = value.getClass();
      if (isStandardType(currentType)) {
        return currentType;
      }

      while (currentType.getInterfaces().length > 0) {
        currentType = currentType.getInterfaces()[max(currentType.getInterfaces().length - 1, 0)];
        if (objectTransformerRegistry.hasTransformer(currentType)) {
          break;
        }
      }

      return currentType;
    }

    return value.getClass();
  }

  static boolean requiresDeepSearch(final Object value) {
    return value.getClass().getName().startsWith(KYORI_PACKAGE_NAME);
  }

  private static boolean isStandardType(final Class<?> type) {
    return String.class.isAssignableFrom(type)
        || Number.class.isAssignableFrom(type)
        || Boolean.class.isAssignableFrom(type)
        || Character.class.isAssignableFrom(type);
  }
}
