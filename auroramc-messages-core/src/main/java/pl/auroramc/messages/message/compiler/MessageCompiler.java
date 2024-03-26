package pl.auroramc.messages.message.compiler;

import static pl.auroramc.messages.placeholder.reflect.PlaceholderEvaluator.getReflectivePlaceholderEvaluator;
import static pl.auroramc.messages.placeholder.resolver.PlaceholderResolver.getPlaceholderResolver;
import static pl.auroramc.messages.placeholder.scanner.PlaceholderScanner.getPlaceholderScanner;
import static pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry.getObjectTransformerRegistry;

import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.decoration.MessageDecoration;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.pack.standard.StandardObjectTransformerPack;

public interface MessageCompiler {

  static MessageCompiler getMessageCompiler(final ObjectTransformerPack... transformerPacks) {
    return new MessageCompilerImpl(
        getPlaceholderResolver(
            getObjectTransformerRegistry(transformerPacks),
            getPlaceholderScanner(),
            getReflectivePlaceholderEvaluator()));
  }

  static MessageCompiler getMessageCompiler() {
    return getMessageCompiler(new StandardObjectTransformerPack());
  }

  CompiledMessage compile(final MutableMessage message, final MessageDecoration... decorations);

  CompiledMessage[] compileChildren(
      final MutableMessage message, final MessageDecoration... decorations);

  CompiledMessage[] compileChildren(
      final MutableMessage message, final String delimiter, final MessageDecoration... decorations);
}
