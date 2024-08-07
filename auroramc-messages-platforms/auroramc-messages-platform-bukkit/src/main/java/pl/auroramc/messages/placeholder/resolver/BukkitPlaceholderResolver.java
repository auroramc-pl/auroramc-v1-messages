package pl.auroramc.messages.placeholder.resolver;

import static me.clip.placeholderapi.PlaceholderAPI.setPlaceholders;
import static org.bukkit.Bukkit.getServer;

import org.bukkit.entity.Player;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.placeholder.evaluator.PlaceholderEvaluator;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;
import pl.auroramc.messages.viewer.Viewer;

public class BukkitPlaceholderResolver extends PlaceholderResolverImpl {

  private static final String PLACEHOLDER_API_PLUGIN_NAME = "PlaceholderAPI";
  private final boolean hasPlaceholderApi;

  private BukkitPlaceholderResolver(
      final ObjectTransformerRegistry transformerRegistry,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderEvaluator placeholderEvaluator) {
    super(transformerRegistry, placeholderScanner, placeholderEvaluator);
    this.hasPlaceholderApi =
        getServer().getPluginManager().isPluginEnabled(PLACEHOLDER_API_PLUGIN_NAME);
  }

  public static BukkitPlaceholderResolver getBukkitPlaceholderResolver(
      final ObjectTransformerRegistry transformerRegistry,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderEvaluator placeholderEvaluator) {
    return new BukkitPlaceholderResolver(
        transformerRegistry, placeholderScanner, placeholderEvaluator);
  }

  @Override
  public MutableMessage resolve(final Viewer viewer, final MutableMessage message) {
    final MutableMessage resolvedMessage = super.resolve(viewer, message);
    if (hasPlaceholderApi && viewer != null && viewer.unwrap() instanceof Player player) {
      return MutableMessage.of(
          setPlaceholders(player, resolvedMessage.getTemplate()), resolvedMessage.getProperty());
    }
    return resolvedMessage;
  }
}
