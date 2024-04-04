package pl.auroramc.messages.placeholder.resolver;

import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.property.MessageProperty;
import pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

public interface PlaceholderResolver<T extends Audience> {

  static <T extends Audience> PlaceholderResolver<T> getPlaceholderResolver(
      final ObjectTransformerRegistry transformerRegistry,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderEvaluator placeholderEvaluator) {
    return new PlaceholderResolverImpl<>(
        transformerRegistry, placeholderScanner, placeholderEvaluator);
  }

  void register(final ObjectTransformerPack... transformerPacks);

  String resolve(final T viewer, final String template, final MessageProperty property);

  String apply(final T viewer, final String template, final MessageProperty property);
}
