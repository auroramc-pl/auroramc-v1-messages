package pl.auroramc.messages.placeholder.transformer.pack.standard;

import static net.kyori.adventure.text.Component.translatable;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.Translatable;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;

public class ComponentByTranslatableTransformer extends ObjectTransformer<Translatable, Component> {

  protected ComponentByTranslatableTransformer() {
    super(Translatable.class);
  }

  @Override
  public Component transform(final Translatable value) {
    return translatable(value.translationKey());
  }
}
