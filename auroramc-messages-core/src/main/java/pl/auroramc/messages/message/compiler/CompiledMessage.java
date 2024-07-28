package pl.auroramc.messages.message.compiler;

import static pl.auroramc.messages.message.compiler.CompiledMessageCollectorUtils.appendComponent;
import static pl.auroramc.messages.message.display.MessageDisplay.CHAT;

import net.kyori.adventure.text.Component;
import pl.auroramc.messages.message.display.MessageDisplay;
import pl.auroramc.messages.viewer.Viewer;

public class CompiledMessage {

  private static final CompiledMessage EMPTY_MESSAGE = new CompiledMessage(Component.empty());
  private static final CompiledMessage NEWLINE_MESSAGE = new CompiledMessage(Component.newline());
  private final Component component;

  CompiledMessage(final Component component) {
    this.component = component;
  }

  public static CompiledMessage empty() {
    return EMPTY_MESSAGE;
  }

  public static CompiledMessage newline() {
    return NEWLINE_MESSAGE;
  }

  public CompiledMessage append(final CompiledMessage message) {
    return new CompiledMessage(appendComponent(component, message.getComponent()));
  }

  public void deliver(final Viewer viewer) {
    viewer.deliver(this, CHAT);
  }

  public void deliver(final Viewer viewer, final MessageDisplay... displays) {
    viewer.deliver(this, displays);
  }

  public Component getComponent() {
    return component;
  }
}
