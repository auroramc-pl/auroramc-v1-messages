package pl.auroramc.messages.placeholder.resolver;

import static me.clip.placeholderapi.PlaceholderAPI.setPlaceholders;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.auroramc.messages.message.property.MessageProperty;
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
  public String apply(
      final CommandSender viewer, final String template, final MessageProperty property) {
    if (viewer instanceof Player player) {
      return setPlaceholders(player, super.apply(viewer, template, property));
    }

    return super.apply(null, template, property);
  }
}
