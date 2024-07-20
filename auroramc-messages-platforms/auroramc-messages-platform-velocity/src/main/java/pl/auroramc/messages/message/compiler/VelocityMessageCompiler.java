package pl.auroramc.messages.message.compiler;

import static pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator.getReflectivePlaceholderEvaluator;
import static pl.auroramc.messages.placeholder.resolver.PlaceholderResolver.getPlaceholderResolver;
import static pl.auroramc.messages.placeholder.scanner.PlaceholderScanner.getPlaceholderScanner;
import static pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry.getObjectTransformerRegistry;

import com.velocitypowered.api.command.CommandSource;
import java.util.concurrent.Executor;
import pl.auroramc.commons.scheduler.Scheduler;
import pl.auroramc.commons.scheduler.caffeine.CaffeineExecutor;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.pack.standard.StandardObjectTransformerPack;
import pl.auroramc.messages.serdes.commons.CommonsObjectTransformerPack;

public interface VelocityMessageCompiler extends MessageCompiler<CommandSource> {

  static VelocityMessageCompiler getVelocityMessageCompiler(
      final Scheduler scheduler, final ObjectTransformerPack... transformerPacks) {
    final PlaceholderScanner placeholderScanner = getPlaceholderScanner();
    final VelocityMessageCompiler messageCompiler =
        getVelocityMessageCompiler(
            new CaffeineExecutor(scheduler),
            getPlaceholderResolver(
                getObjectTransformerRegistry(transformerPacks),
                placeholderScanner,
                getReflectivePlaceholderEvaluator()));
    messageCompiler.register(new CommonsObjectTransformerPack());
    messageCompiler.register(new StandardObjectTransformerPack());
    return messageCompiler;
  }

  static VelocityMessageCompiler getVelocityMessageCompiler(
      final Executor executor, final PlaceholderResolver<CommandSource> placeholderResolver) {
    return new VelocityMessageCompilerImpl(executor, placeholderResolver);
  }
}
