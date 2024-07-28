package pl.auroramc.messages.viewer;

import static java.time.Duration.ofSeconds;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;

class BukkitViewerService implements BukkitViewerFacade {

  private final LoadingCache<UUID, BukkitViewer> viewerByUniqueId;
  private final Server server;

  BukkitViewerService(final Server server) {
    this.viewerByUniqueId =
        Caffeine.newBuilder().expireAfterAccess(ofSeconds(30)).build(this::createViewerByUniqueId);
    this.server = server;
  }

  @Override
  public BukkitViewer getViewerByUniqueId(final UUID uniqueId) {
    return viewerByUniqueId.get(uniqueId);
  }

  @Override
  public BukkitViewer createViewerByUniqueId(final UUID uniqueId) {
    final Player player = server.getPlayer(uniqueId);
    if (player == null) {
      throw new ViewerInstantiationException(
          "Could not create viewer for player identified by %s.".formatted(uniqueId.toString()));
    }
    return new BukkitViewer(player);
  }
}
