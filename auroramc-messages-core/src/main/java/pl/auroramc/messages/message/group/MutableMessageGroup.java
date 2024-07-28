package pl.auroramc.messages.message.group;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.viewer.Viewer;

public record MutableMessageGroup(Map<MutableMessage, Set<Viewer>> messagesByReceivers) {

  public static MutableMessageGroup grouping() {
    return new MutableMessageGroup(new HashMap<>());
  }

  public MutableMessageGroup message(final MutableMessage message, final Viewer... receivers) {
    messagesByReceivers.put(message, Set.of(receivers));
    return this;
  }
}
