package pl.auroramc.messages.viewer;

import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.compiler.CompiledMessage;
import pl.auroramc.messages.message.display.MessageDisplay;

public class BukkitViewer implements Viewer {

  private final Audience audience;

  BukkitViewer(final Audience audience) {
    this.audience = audience;
  }

  public static BukkitViewer wrap(final Audience audience) {
    return new BukkitViewer(audience);
  }

  @Override
  public void deliver(final CompiledMessage message, final MessageDisplay... displays) {
    message.deliver(audience, displays);
  }
}
