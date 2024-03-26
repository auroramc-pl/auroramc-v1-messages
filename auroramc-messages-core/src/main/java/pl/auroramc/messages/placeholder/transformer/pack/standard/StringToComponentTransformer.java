package pl.auroramc.messages.placeholder.transformer.pack.standard;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import net.kyori.adventure.text.Component;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;

class StringToComponentTransformer implements ObjectTransformer<Component, String> {

  StringToComponentTransformer() {}

  @Override
  public boolean supports(final Object value) {
    return Component.class.isAssignableFrom(value.getClass());
  }

  @Override
  public String transform(final Component value) {
    return miniMessage().serialize(value);
  }

  @Override
  public Class<?> getType() {
    return Component.class;
  }
}
