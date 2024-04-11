package pl.auroramc.messages.message.compiler;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import net.kyori.adventure.text.Component;

final class CompiledMessageCollectorUtils {

  private CompiledMessageCollectorUtils() {}

  static boolean isEmptyComponent(final Component component) {
    return miniMessage().serialize(component).isEmpty();
  }

  static Component appendComponent(final Component original, final Component appendix) {
    return isEmptyComponent(original) ? original : appendix.appendNewline().append(original);
  }
}
