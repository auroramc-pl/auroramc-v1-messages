package pl.auroramc.messages.placeholder.resolver;

import static pl.auroramc.messages.placeholder.resolver.PlaceholderResolverUtils.getParentType;
import static pl.auroramc.messages.placeholder.resolver.PlaceholderResolverUtils.getPlaceholderKey;
import static pl.auroramc.messages.placeholder.scanner.PlaceholderScannerUtils.PATH_CHILDREN_DELIMITER;

import java.util.Map.Entry;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.property.MessageProperty;
import pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

class PlaceholderResolverImpl<T extends Audience> implements PlaceholderResolver<T> {

  private static final int TRANSFORMATION_MAXIMUM_TRIES = 5;
  private final ObjectTransformerRegistry transformerRegistry;
  private final PlaceholderScanner placeholderScanner;
  private final PlaceholderEvaluator placeholderEvaluator;

  PlaceholderResolverImpl(
      final ObjectTransformerRegistry transformerRegistry,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderEvaluator placeholderEvaluator) {
    this.transformerRegistry = transformerRegistry;
    this.placeholderScanner = placeholderScanner;
    this.placeholderEvaluator = placeholderEvaluator;
  }

  @Override
  public void register(final ObjectTransformerPack... transformerPacks) {
    transformerRegistry.register(transformerPacks);
  }

  @Override
  public String resolve(final T viewer, final String template, MessageProperty property) {
    final String[] paths = placeholderScanner.getPlaceholderPaths(template);
    for (final String path : paths) {
      if (property.getValueByPath(path) != null) {
        continue;
      }

      if (placeholderScanner.hasPathChildren(path)) {
        final String parentPath = path.substring(0, path.indexOf(PATH_CHILDREN_DELIMITER));
        final String childPath = path.substring(path.indexOf(PATH_CHILDREN_DELIMITER) + 1);
        property = traverse(property, path, parentPath, null, childPath);
      }
    }

    return apply(viewer, template, property);
  }

  @Override
  public String apply(final T viewer, String template, final MessageProperty property) {
    for (final Entry<String, Object> valueByPath : property.getValuesByPaths().entrySet()) {
      template =
          template.replace(
              getPlaceholderKey(valueByPath.getKey()), transform(valueByPath.getValue()));
    }

    return template;
  }

  private String transform(final Object value) {
    return transform(value, 0);
  }

  private String transform(final Object value, int tries) {
    if (tries > TRANSFORMATION_MAXIMUM_TRIES) {
      return value.toString();
    }

    final ObjectTransformer<Object, Object> transformer =
        transformerRegistry.getTransformer(getParentType(transformerRegistry, value));
    if (transformer == null) {
      return value.toString();
    }

    final Object transformedValue = transformer.transform(value);
    if (transformedValue instanceof String) {
      return transformedValue.toString();
    }

    return transform(transformedValue, tries + 1);
  }

  private MessageProperty traverse(
      MessageProperty property,
      final String initialPath,
      final String parentPath,
      final String parentPathPrev,
      final String childPath) {
    if (parentPathPrev != null && property.getValueByPath(parentPath) == null) {
      property =
          property.placeholder(
              parentPath,
              placeholderEvaluator.evaluate(property.getValueByPath(parentPathPrev), parentPath));
    }

    if (placeholderScanner.hasPathChildren(childPath)) {
      final int nextParentPathStartIndex = childPath.indexOf(PATH_CHILDREN_DELIMITER);
      final int nextParentPathEndIndex =
          childPath.indexOf(PATH_CHILDREN_DELIMITER, nextParentPathStartIndex);

      return traverse(
          property,
          initialPath,
          childPath.substring(0, nextParentPathEndIndex),
          parentPath,
          childPath.substring(nextParentPathEndIndex + 1));
    }

    final Object parentValue = property.getValueByPath(parentPath);

    final int childPathStartIndex = childPath.indexOf(PATH_CHILDREN_DELIMITER);
    final int childPathEndIndex =
        childPath.indexOf(PATH_CHILDREN_DELIMITER, childPathStartIndex + 1);
    final Object childValue =
        placeholderEvaluator.evaluate(
            parentValue,
            property.getTargetByPath(
                placeholderScanner.hasPathChildren(childPath) && childPathEndIndex != -1
                    ? childPath.substring(0, childPathStartIndex)
                    : childPath));

    return property.placeholder(initialPath, childValue);
  }
}
