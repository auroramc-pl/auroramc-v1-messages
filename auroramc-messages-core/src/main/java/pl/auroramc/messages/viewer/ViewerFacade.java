package pl.auroramc.messages.viewer;

import java.util.UUID;

public interface ViewerFacade<V extends Viewer> {

  V getOrCreateViewerByUniqueId(final UUID uniqueId);

  V getViewerByUniqueId(final UUID uniqueId);

  V createViewerByUniqueId(final UUID uniqueId);

  void deleteViewerByUniqueId(final UUID uniqueId);
}
