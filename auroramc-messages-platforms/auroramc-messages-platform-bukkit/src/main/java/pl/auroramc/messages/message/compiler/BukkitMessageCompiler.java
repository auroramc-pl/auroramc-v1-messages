package pl.auroramc.messages.message.compiler;

import static pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator.getReflectivePlaceholderEvaluator;
import static pl.auroramc.messages.placeholder.resolver.BukkitPlaceholderResolver.getBukkitPlaceholderResolver;
import static pl.auroramc.messages.placeholder.scanner.PlaceholderScanner.getPlaceholderScanner;
import static pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry.getObjectTransformerRegistry;

import java.util.concurrent.Executor;
import org.bukkit.command.CommandSender;
import pl.auroramc.commons.scheduler.Scheduler;
import pl.auroramc.commons.scheduler.caffeine.CaffeineExecutor;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.pack.BukkitObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformerPack;
import pl.auroramc.messages.placeholder.transformer.pack.standard.StandardObjectTransformerPack;
import pl.auroramc.messages.serdes.commons.CommonsObjectTransformerPack;

public interface BukkitMessageCompiler extends MessageCompiler<CommandSender> {

  static BukkitMessageCompiler getBukkitMessageCompiler(
      final Scheduler scheduler, final ObjectTransformerPack... transformerPacks) {
    final PlaceholderScanner placeholderScanner = getPlaceholderScanner();
    final BukkitMessageCompiler messageCompiler =
        getBukkitMessageCompiler(
            new CaffeineExecutor(scheduler),
            getBukkitPlaceholderResolver(
                getObjectTransformerRegistry(transformerPacks),
                placeholderScanner,
                getReflectivePlaceholderEvaluator()));
    messageCompiler.register(new CommonsObjectTransformerPack());
    messageCompiler.register(new StandardObjectTransformerPack());
    messageCompiler.register(new BukkitObjectTransformerPack());
    return messageCompiler;
  }

  static BukkitMessageCompiler getBukkitMessageCompiler(
      final Executor executor, final PlaceholderResolver<CommandSender> placeholderResolver) {
    return new BukkitMessageCompilerImpl(executor, placeholderResolver);
  }
}
