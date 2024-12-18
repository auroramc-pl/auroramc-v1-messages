package pl.auroramc.messages.placeholder.resolver;

import static pl.auroramc.commons.format.StringUtils.BLANK;
import static pl.auroramc.messages.placeholder.resolver.PlaceholderResolverUtils.getParentType;
import static pl.auroramc.messages.placeholder.scanner.PlaceholderScannerUtils.PATH_CHILDREN_DELIMITER;

import net.kyori.adventure.text.ComponentLike;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.property.MessageProperty;
import pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;
import pl.auroramc.messages.viewer.Viewer;

class PlaceholderResolverImpl implements PlaceholderResolver {

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
  public MutableMessage resolve(final Viewer viewer, final MutableMessage message) {
    MessageProperty property = message.getProperty();

    final String[] paths = placeholderScanner.getPlaceholderPaths(message.getTemplate());
    for (final String path : paths) {
      if (property.getValueByPath(path) != null) {
        continue;
      }

      if (placeholderScanner.hasPathChildren(path)) {
        final String parentPath = path.substring(0, path.indexOf(PATH_CHILDREN_DELIMITER));
        final String childPath = path.substring(path.indexOf(PATH_CHILDREN_DELIMITER) + 1);
        property = traverse(viewer, property, path, parentPath, null, childPath);
      }
    }

    return MutableMessage.of(message.getTemplate(), property);
  }

  @Override
  public Object transform(final Viewer viewer, final Object value) {
    return transform(viewer, value, 0);
  }

  private Object transform(final Viewer viewer, final Object value, int tries) {
    if (value == null) {
      return BLANK;
    }

    if (tries > TRANSFORMATION_MAXIMUM_TRIES) {
      return value.toString();
    }

    final ObjectTransformer<Object, Object> transformer =
        transformerRegistry.getTransformer(getParentType(transformerRegistry, value));
    if (transformer == null) {
      return value;
    }

    final Object transformedValue = transformer.transform(viewer, value);
    if (transformedValue instanceof String || transformedValue instanceof ComponentLike) {
      return transformedValue;
    }

    return transform(viewer, transformedValue, tries + 1);
  }

  private MessageProperty traverse(
      final Viewer viewer,
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
          viewer,
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
    Object childValue =
        placeholderEvaluator.evaluate(
            parentValue,
            property.getTargetByPath(
                placeholderScanner.hasPathChildren(childPath) && childPathEndIndex != -1
                    ? childPath.substring(0, childPathStartIndex)
                    : childPath));
    childValue = transform(viewer, childValue);

    return property.placeholder(initialPath, childValue);
  }
}
