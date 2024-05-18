package pl.auroramc.messages.viewer;

import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.compiler.CompiledMessage;
import pl.auroramc.messages.message.display.MessageDisplay;

public class VelocityViewer implements Viewer {

  private final Audience audience;

  VelocityViewer(final Audience audience) {
    this.audience = audience;
  }

  public static VelocityViewer wrap(final Audience audience) {
    return new VelocityViewer(audience);
  }

  @Override
  public void deliver(final CompiledMessage message, final MessageDisplay... displays) {
    message.deliver(audience, displays);
  }
}
