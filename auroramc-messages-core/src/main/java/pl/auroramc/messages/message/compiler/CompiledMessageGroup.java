package pl.auroramc.messages.message.compiler;

import java.util.Map;
import java.util.Map.Entry;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.display.MessageDisplay;

public record CompiledMessageGroup(Map<CompiledMessage, Audience> messagesByReceivers) {

  public void deliver(final MessageDisplay... displays) {
    for (final Entry<CompiledMessage, Audience> entry : messagesByReceivers.entrySet()) {
      final CompiledMessage message = entry.getKey();
      entry.getValue().forEachAudience(receiver -> message.deliver(receiver, displays));
    }
  }
}
