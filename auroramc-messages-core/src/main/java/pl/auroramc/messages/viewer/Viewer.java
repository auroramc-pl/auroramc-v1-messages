package pl.auroramc.messages.viewer;

import static pl.auroramc.messages.message.display.MessageDisplay.CHAT;

import java.util.Locale;
import pl.auroramc.messages.message.compiler.CompiledMessage;
import pl.auroramc.messages.message.display.MessageDisplay;

public interface Viewer {

  default void deliver(final CompiledMessage message) {
    deliver(message, CHAT);
  }

  void deliver(final CompiledMessage message, final MessageDisplay... displays);

  Locale getLocale();

  Object unwrap();
}
