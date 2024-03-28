package pl.auroramc.messages.placeholder.resolver;

import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.placeholder.context.PlaceholderContext;
import pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

public interface PlaceholderResolver<T extends Audience> {

  static <T extends Audience> PlaceholderResolver<T> getPlaceholderResolver(
      final ObjectTransformerRegistry transformerRegistry,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderEvaluator placeholderEvaluator) {
    return new PlaceholderResolverImpl<>(
        transformerRegistry, placeholderScanner, placeholderEvaluator);
  }

  String resolve(final T viewer, final String template, final PlaceholderContext context);

  String apply(final T viewer, final String template, final PlaceholderContext context);
}