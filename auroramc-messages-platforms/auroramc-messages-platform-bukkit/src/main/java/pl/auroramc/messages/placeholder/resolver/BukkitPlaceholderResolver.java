package pl.auroramc.messages.placeholder.resolver;

import static me.clip.placeholderapi.PlaceholderAPI.setPlaceholders;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

public class BukkitPlaceholderResolver extends PlaceholderResolverImpl<CommandSender> {

  private BukkitPlaceholderResolver(
      final ObjectTransformerRegistry transformerRegistry,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderEvaluator placeholderEvaluator) {
    super(transformerRegistry, placeholderScanner, placeholderEvaluator);
  }

  public static BukkitPlaceholderResolver getBukkitPlaceholderResolver(
      final ObjectTransformerRegistry transformerRegistry,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderEvaluator placeholderEvaluator) {
    return new BukkitPlaceholderResolver(
        transformerRegistry, placeholderScanner, placeholderEvaluator);
  }

  @Override
  public MutableMessage resolve(final CommandSender viewer, final MutableMessage message) {
    final MutableMessage resolvedMessage = super.resolve(viewer, message);
    if (viewer instanceof Player player) {
      return MutableMessage.of(
          setPlaceholders(player, resolvedMessage.getTemplate()), resolvedMessage.getProperty());
    }

    return resolvedMessage;
  }
}
