package pl.auroramc.messages.placeholder.resolver;

import pl.auroramc.messages.placeholder.context.PlaceholderContext;
import pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

public interface PlaceholderResolver {

  static PlaceholderResolver getPlaceholderResolver(
      final ObjectTransformerRegistry transformerRegistry,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderEvaluator placeholderEvaluator) {
    return new PlaceholderResolverImpl(
        transformerRegistry, placeholderScanner, placeholderEvaluator);
  }

  String resolve(final String template, final PlaceholderContext context);
}
