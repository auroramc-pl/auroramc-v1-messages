package pl.auroramc.messages.viewer;

import com.velocitypowered.api.proxy.ProxyServer;

public interface VelocityViewerFacade extends ViewerFacade<VelocityViewer> {

  static VelocityViewerFacade getVelocityViewerFacade(final ProxyServer server) {
    return new VelocityViewerService(server);
  }
}
