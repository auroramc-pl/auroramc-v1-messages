package pl.auroramc.messages.message.compiler;

import static pl.auroramc.messages.message.compiler.CompiledMessageCollectorUtils.appendComponent;
import static pl.auroramc.messages.message.display.MessageDisplay.ACTION_BAR;
import static pl.auroramc.messages.message.display.MessageDisplay.CHAT;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import pl.auroramc.messages.message.display.MessageDisplay;

public class CompiledMessage {

  private static final CompiledMessage EMPTY_MESSAGE = new CompiledMessage(Component.empty());
  private final Component component;

  CompiledMessage(final Component component) {
    this.component = component;
  }

  public static CompiledMessage empty() {
    return EMPTY_MESSAGE;
  }

  public CompiledMessage append(final CompiledMessage message) {
    return new CompiledMessage(appendComponent(component, message.getComponent()));
  }

  public void deliver(final Audience audience) {
    deliver(audience, CHAT);
  }

  public void deliver(final Audience audience, final MessageDisplay... displays) {
    if (displays.length == 0) {
      throw new CompiledMessageDeliveringException("At least one display must be provided");
    }

    for (final MessageDisplay display : displays) {
      deliver(audience, display);
    }
  }

  private void deliver(final Audience audience, final MessageDisplay display) {
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
