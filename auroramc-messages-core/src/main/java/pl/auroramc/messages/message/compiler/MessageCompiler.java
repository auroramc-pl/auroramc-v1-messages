package pl.auroramc.messages.message.compiler;

import static pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator.getReflectivePlaceholderEvaluator;
import static pl.auroramc.messages.placeholder.resolver.PlaceholderResolver.getPlaceholderResolver;
import static pl.auroramc.messages.placeholder.scanner.PlaceholderScanner.getPlaceholderScanner;
import static pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry.getObjectTransformerRegistry;

import java.util.concurrent.Executor;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.decoration.MessageDecoration;
import pl.auroramc.messages.message.group.MutableMessageGroup;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.pack.standard.StandardObjectTransformerPack;
import pl.auroramc.messages.viewer.Viewer;

public interface MessageCompiler {

  static MessageCompiler getMessageCompiler(
      final Executor executor, final ObjectTransformerPack... transformerPacks) {
    final PlaceholderScanner placeholderScanner = getPlaceholderScanner();
    return getMessageCompiler(
        executor,
        getPlaceholderResolver(
            getObjectTransformerRegistry(transformerPacks),
            placeholderScanner,
            getReflectivePlaceholderEvaluator()));
  }

  static MessageCompiler getMessageCompiler(final Executor executor) {
    return getMessageCompiler(executor, new StandardObjectTransformerPack());
  }

  static MessageCompiler getMessageCompiler(
      final Executor executor, final PlaceholderResolver placeholderResolver) {
    return new MessageCompilerImpl(executor, placeholderResolver);
  }

  default CompiledMessage compile(
      final MutableMessage message, final MessageDecoration... decorations) {
    return compile(null, message, decorations);
  }

  CompiledMessage compile(
      final Viewer viewer, final MutableMessage message, final MessageDecoration... decorations);

  default CompiledMessage[] compileChildren(
      final MutableMessage message, final MessageDecoration... decorations) {
    return compileChildren(null, message, decorations);
  }

  default CompiledMessage[] compileChildren(
      final MutableMessage message,
      final String delimiter,
      final MessageDecoration... decorations) {
    return compileChildren(null, message, delimiter, decorations);
  }

  CompiledMessage[] compileChildren(
      final Viewer viewer, final MutableMessage message, final MessageDecoration... decorations);

  CompiledMessage[] compileChildren(
      final Viewer viewer,
      final MutableMessage message,
      final String delimiter,
      final MessageDecoration... decorations);

  CompiledMessageGroup compileGroup(
      final MutableMessageGroup messageGroup, final MessageDecoration... decorations);

  void register(final ObjectTransformerPack... transformerPacks);
}
