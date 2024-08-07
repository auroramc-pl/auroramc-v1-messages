package pl.auroramc.messages.viewer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Server;
import org.bukkit.entity.Player;

class BukkitViewerService implements BukkitViewerFacade {

  private final Server server;
  private final Map<UUID, BukkitViewer> viewerByUniqueId;

  BukkitViewerService(final Server server) {
    this.server = server;
    this.viewerByUniqueId = new ConcurrentHashMap<>();
  }

  @Override
  public BukkitViewer getOrCreateViewerByUniqueId(final UUID uniqueId) {
    final BukkitViewer cachedViewer = viewerByUniqueId.get(uniqueId);
    if (cachedViewer != null) {
      return cachedViewer;
    }
    return createViewerByUniqueId(uniqueId);
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
    return cacheAndGetViewer(uniqueId, new BukkitViewer(player));
  }

  @Override
  public void deleteViewerByUniqueId(final UUID uniqueId) {
    viewerByUniqueId.remove(uniqueId);
  }

  private BukkitViewer cacheAndGetViewer(final UUID uniqueId, final BukkitViewer viewer) {
    viewerByUniqueId.put(uniqueId, viewer);
    return viewer;
  }
}
