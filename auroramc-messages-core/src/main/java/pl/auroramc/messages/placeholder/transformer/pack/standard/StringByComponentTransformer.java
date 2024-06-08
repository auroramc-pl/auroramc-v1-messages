package pl.auroramc.messages.placeholder.transformer.pack.standard;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import net.kyori.adventure.text.Component;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;

class StringByComponentTransformer extends ObjectTransformer<Component, String> {

  StringByComponentTransformer() {
    super(Component.class);
  }

  @Override
  public String transform(final Component value) {
    return miniMessage().serialize(value);
  }
}
