package pl.auroramc.messages.message.compiler;

import static pl.auroramc.messages.message.display.MessageDisplay.CHAT;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import pl.auroramc.messages.message.display.MessageDisplay;
import pl.auroramc.messages.viewer.Viewer;

public record CompiledMessageGroup(Map<CompiledMessage, Set<Viewer>> messagesByReceivers) {

  public void deliver() {
    deliver(CHAT);
  }

  public void deliver(final MessageDisplay... displays) {
    for (final Entry<CompiledMessage, Set<Viewer>> entry : messagesByReceivers.entrySet()) {
      final CompiledMessage message = entry.getKey();
      entry.getValue().forEach(receiver -> message.deliver(receiver, displays));
    }
  }
}
