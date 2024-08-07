package pl.auroramc.messages.viewer;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class VelocityViewerService implements VelocityViewerFacade {

  private final ProxyServer server;
  private final Map<UUID, VelocityViewer> viewerByUniqueId;

  VelocityViewerService(final ProxyServer server) {
    this.server = server;
    this.viewerByUniqueId = new ConcurrentHashMap<>();
  }

  @Override
  public VelocityViewer getOrCreateViewerByUniqueId(final UUID uniqueId) {
    final VelocityViewer cachedViewer = viewerByUniqueId.get(uniqueId);
    if (cachedViewer != null) {
      return cachedViewer;
    }
    return createViewerByUniqueId(uniqueId);
  }

  @Override
  public VelocityViewer getViewerByUniqueId(final UUID uniqueId) {
    return viewerByUniqueId.get(uniqueId);
  }

  @Override
  public VelocityViewer createViewerByUniqueId(final UUID uniqueId) {
    final Player player = server
        .getPlayer(uniqueId)
        .orElseThrow(
            () ->
                new ViewerInstantiationException(
                    "Could not create viewer for player identified by %s."
                        .formatted(uniqueId.toString())));
    return cacheAndGetViewer(uniqueId, new VelocityViewer(player));
  }

  @Override
  public void deleteViewerByUniqueId(final UUID uniqueId) {
    viewerByUniqueId.remove(uniqueId);
  }

  private VelocityViewer cacheAndGetViewer(final UUID uniqueId, final VelocityViewer viewer) {
    viewerByUniqueId.put(uniqueId, viewer);
    return viewer;
  }
}
