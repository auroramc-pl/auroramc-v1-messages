package pl.auroramc.messages.message.decoration;

import static net.kyori.adventure.text.format.TextDecoration.ITALIC;
import static net.kyori.adventure.text.format.TextDecoration.State.FALSE;
import static pl.auroramc.messages.message.decoration.MessageDecoration.decorate;

public final class MessageDecorations {

  public static final MessageDecoration NO_CURSIVE = decorate(ITALIC, FALSE);

  private MessageDecorations() {}
}
