package pl.auroramc.messages.placeholder.resolver;

import static pl.auroramc.messages.placeholder.resolver.PlaceholderResolverUtils.getParentType;
import static pl.auroramc.messages.placeholder.resolver.PlaceholderResolverUtils.getPlaceholderKey;
import static pl.auroramc.messages.placeholder.scanner.PlaceholderScannerUtils.PATH_CHILDREN_DELIMITER;

import java.util.Map.Entry;
import pl.auroramc.messages.placeholder.context.PlaceholderContext;
import pl.auroramc.messages.placeholder.reflect.PlaceholderEvaluator;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

class PlaceholderResolverImpl implements PlaceholderResolver {

  private final ObjectTransformerRegistry objectTransformerRegistry;
  private final PlaceholderScanner placeholderScanner;
  private final PlaceholderEvaluator placeholderEvaluator;

  PlaceholderResolverImpl(
      final ObjectTransformerRegistry transformerRegistry,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderEvaluator placeholderEvaluator) {
    this.objectTransformerRegistry = transformerRegistry;
    this.placeholderScanner = placeholderScanner;
    this.placeholderEvaluator = placeholderEvaluator;
  }

  @Override
  public String resolve(final String template, PlaceholderContext context) {
    final String[] paths = placeholderScanner.getPlaceholderPaths(template);
    for (final String path : paths) {
      if (context.getValueByPath(path) != null) {
        continue;
      }

      if (placeholderScanner.hasPathChildren(path)) {
        final String parentPath = path.substring(0, path.indexOf(PATH_CHILDREN_DELIMITER));
        final String childPath = path.substring(path.indexOf(PATH_CHILDREN_DELIMITER) + 1);
        context = traverse(context, parentPath, null, childPath);
      }
    }

    return apply(template, context);
  }

  private PlaceholderContext traverse(
      PlaceholderContext context,
      final String parentPath,
      final String parentPathPrev,
      final String childPath) {
    if (parentPathPrev != null
        && placeholderScanner.hasPathChildren(parentPath)
        && context.getValueByPath(parentPath) == null) {
      context =
          context.placeholder(
              parentPath,
              placeholderEvaluator.evaluate(
                  context.getValueByPath(parentPathPrev),
                  parentPath.substring(parentPath.lastIndexOf(PATH_CHILDREN_DELIMITER) + 1)));
    }

    final boolean hasNextChildren = placeholderScanner.hasPathChildren(childPath);
    final Object parentValue = context.getValueByPath(parentPath);
    final Object childValue =
        placeholderEvaluator.evaluate(
            parentValue, childPath.substring(childPath.indexOf(PATH_CHILDREN_DELIMITER) + 1));

    context =
        context.placeholder(placeholderScanner.getMergedPath(parentPath, childPath), childValue);
    if (hasNextChildren) {
      final String nextParentPath =
          placeholderScanner.getMergedPath(
              parentPath, childPath.substring(0, childPath.indexOf(PATH_CHILDREN_DELIMITER)));
      final String nextChildPath =
          childPath.substring(childPath.indexOf(PATH_CHILDREN_DELIMITER) + 1);
      return traverse(context, nextParentPath, parentPath, nextChildPath);
    }

    return context;
  }

  private String apply(String template, final PlaceholderContext context) {
    for (final Entry<String, Object> valueByPath : context.getValuesByPaths().entrySet()) {
      final String placeholderKey = getPlaceholderKey(valueByPath.getKey());

      final ObjectTransformer<Object, Object> objectTransformer =
          objectTransformerRegistry.getTransformer(
              getParentType(objectTransformerRegistry, valueByPath.getValue()));
      if (objectTransformer == null) {
        template = template.replace(placeholderKey, valueByPath.getValue().toString());
        continue;
      }

      final Object transformedValue = objectTransformer.transform(valueByPath.getValue());
      template = template.replace(placeholderKey, transformedValue.toString());
    }

    return template;
  }
}
