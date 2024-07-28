package pl.auroramc.messages.placeholder.resolver;

import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;
import pl.auroramc.messages.viewer.Viewer;

public interface PlaceholderResolver {

  static PlaceholderResolver getPlaceholderResolver(
      final ObjectTransformerRegistry transformerRegistry,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderEvaluator placeholderEvaluator) {
    return new PlaceholderResolverImpl(
        transformerRegistry, placeholderScanner, placeholderEvaluator);
  }

  void register(final ObjectTransformerPack... transformerPacks);

  MutableMessage resolve(final Viewer viewer, final MutableMessage message);

  Object transform(final Viewer viewer, final Object value);
}
