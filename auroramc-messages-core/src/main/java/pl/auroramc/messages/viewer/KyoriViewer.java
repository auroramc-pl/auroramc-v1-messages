package pl.auroramc.messages.viewer;

import static java.util.Locale.ENGLISH;
import static pl.auroramc.messages.message.display.MessageDisplay.ACTION_BAR;
import static pl.auroramc.messages.message.display.MessageDisplay.CHAT;

import java.util.Locale;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import pl.auroramc.messages.message.compiler.CompiledMessage;
import pl.auroramc.messages.message.display.MessageDisplay;

public class KyoriViewer implements Viewer {

  private static final Locale FALLBACK_LOCALE = ENGLISH;
  private final Audience audience;
  private Locale currentLocale;

  KyoriViewer(final Audience audience) {
    this.audience = audience;
  }

  public static KyoriViewer wrap(final Audience audience) {
    return new KyoriViewer(audience);
  }

  @Override
  public void deliver(final CompiledMessage message, final MessageDisplay... displays) {
    if (displays.length == 0) {
      throw new ViewerDeliveryException("At least one display must be provided");
    }

    for (final MessageDisplay display : displays) {
      deliver(message, display);
    }
  }

  @Override
  public Locale getLocale() {
    return FALLBACK_LOCALE;
  }

  @Override
  public Object unwrap() {
    return audience;
  }

  public Locale getCurrentLocale() {
    return currentLocale;
  }

  public void setCurrentLocale(final Locale currentLocale) {
    this.currentLocale = currentLocale;
  }

  private void deliver(final CompiledMessage message, final MessageDisplay display) {
    final Component component = message.getComponent();
    if (display == CHAT) {
      audience.sendMessage(component);
    } else if (display == ACTION_BAR) {
      audience.sendActionBar(component);
    }
  }
}
