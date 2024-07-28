package pl.auroramc.messages.viewer;

import org.bukkit.Server;

public interface BukkitViewerFacade extends ViewerFacade<BukkitViewer> {

  static BukkitViewerFacade getBukkitViewerFacade(final Server server) {
    return new BukkitViewerService(server);
  }
}
