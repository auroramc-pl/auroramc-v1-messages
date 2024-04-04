package pl.auroramc.messages.message.group;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.MutableMessage;

public record MutableMessageGroup(Map<MutableMessage, Set<Audience>> messagesByReceivers) {

  public static MutableMessageGroup grouping() {
    return new MutableMessageGroup(new HashMap<>());
  }

  public MutableMessageGroup message(final MutableMessage message, final Audience... receivers) {
    messagesByReceivers.put(message, Set.of(receivers));
    return this;
  }
}
