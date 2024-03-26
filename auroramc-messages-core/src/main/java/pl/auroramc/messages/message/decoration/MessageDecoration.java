package pl.auroramc.messages.message.decoration;

import net.kyori.adventure.text.format.TextDecoration;

public record MessageDecoration(TextDecoration decoration, TextDecoration.State state) {

  public static MessageDecoration decorate(
      final TextDecoration decoration, final TextDecoration.State state) {
    return new MessageDecoration(decoration, state);
  }
}
