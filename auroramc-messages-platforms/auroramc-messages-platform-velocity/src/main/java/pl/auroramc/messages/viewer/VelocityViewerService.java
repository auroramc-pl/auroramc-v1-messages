package pl.auroramc.messages.viewer;

import static java.time.Duration.ofSeconds;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.velocitypowered.api.proxy.ProxyServer;
import java.util.UUID;

class VelocityViewerService implements VelocityViewerFacade {

  private final LoadingCache<UUID, VelocityViewer> viewerByUniqueId;
  private final ProxyServer server;

  VelocityViewerService(final ProxyServer server) {
    this.viewerByUniqueId =
        Caffeine.newBuilder().expireAfterAccess(ofSeconds(30)).build(this::createViewerByUniqueId);
    this.server = server;
  }

  @Override
  public VelocityViewer getViewerByUniqueId(final UUID uniqueId) {
    return viewerByUniqueId.get(uniqueId);
  }

  @Override
  public VelocityViewer createViewerByUniqueId(final UUID uniqueId) {
    return server
        .getPlayer(uniqueId)
        .map(VelocityViewer::new)
        .orElseThrow(
            () ->
                new ViewerInstantiationException(
                    "Could not create viewer for player identified by %s."
                        .formatted(uniqueId.toString())));
  }
}
