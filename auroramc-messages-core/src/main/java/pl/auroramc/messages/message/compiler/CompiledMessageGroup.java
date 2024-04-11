package pl.auroramc.messages.message.compiler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.display.MessageDisplay;

public record CompiledMessageGroup(Map<CompiledMessage, Set<Audience>> messagesByReceivers) {

  public void deliver(final MessageDisplay... displays) {
    for (final Entry<CompiledMessage, Set<Audience>> entry : messagesByReceivers.entrySet()) {
      final CompiledMessage message = entry.getKey();
      for (final Audience receiver : entry.getValue()) {
        message.deliver(receiver, displays);
      }
    }
  }
}
