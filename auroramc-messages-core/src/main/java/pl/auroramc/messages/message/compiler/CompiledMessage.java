package pl.auroramc.messages.message.compiler;

import static pl.auroramc.messages.message.display.MessageDisplay.ACTION_BAR;
import static pl.auroramc.messages.message.display.MessageDisplay.CHAT;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import pl.auroramc.messages.message.display.MessageDisplay;

public class CompiledMessage {

  private final Component component;

  CompiledMessage(final Component component) {
    this.component = component;
  }

  public void render(final Audience audience, final MessageDisplay... displays) {
    if (displays.length == 0) {
      throw new CompiledMessageRenderingException("At least one display must be provided");
    }

    for (final MessageDisplay display : displays) {
      render(audience, display);
    }
  }

  private void render(final Audience audience, final MessageDisplay display) {
    if (display == CHAT) {
      audience.sendMessage(component);
    } else if (display == ACTION_BAR) {
      audience.sendActionBar(component);
    }
  }

  public Component getComponent() {
    return component;
  }
}
