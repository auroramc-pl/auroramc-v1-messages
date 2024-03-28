package pl.auroramc.messages.message.compiler;

import static pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator.getReflectivePlaceholderEvaluator;
import static pl.auroramc.messages.placeholder.resolver.PlaceholderResolver.getPlaceholderResolver;
import static pl.auroramc.messages.placeholder.scanner.PlaceholderScanner.getPlaceholderScanner;
import static pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry.getObjectTransformerRegistry;

import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.decoration.MessageDecoration;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.pack.standard.StandardObjectTransformerPack;

public interface MessageCompiler<T extends Audience> {

  static <T extends Audience> MessageCompiler<T> getMessageCompiler(
      final ObjectTransformerPack... transformerPacks) {
    return getMessageCompiler(
        getPlaceholderResolver(
            getObjectTransformerRegistry(transformerPacks),
            getPlaceholderScanner(),
            getReflectivePlaceholderEvaluator()));
  }

  static <T extends Audience> MessageCompiler<T> getMessageCompiler() {
    return getMessageCompiler(new StandardObjectTransformerPack());
  }

  static <T extends Audience> MessageCompiler<T> getMessageCompiler(
      final PlaceholderResolver<T> placeholderResolver) {
    return new MessageCompilerImpl<>(placeholderResolver);
  }

  default CompiledMessage compile(
      final MutableMessage message, final MessageDecoration... decorations) {
    return compile(null, message, decorations);
  }

  CompiledMessage compile(
      final T viewer, final MutableMessage message, final MessageDecoration... decorations);

  CompiledMessage[] compileChildren(
      final T viewer, final MutableMessage message, final MessageDecoration... decorations);

  CompiledMessage[] compileChildren(
      final T viewer,
      final MutableMessage message,
      final String delimiter,
      final MessageDecoration... decorations);
}
