package pl.auroramc.messages.placeholder.transformer.pack.standard;

import static net.kyori.adventure.text.Component.translatable;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.Translatable;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;
import pl.auroramc.messages.viewer.Viewer;

public class ComponentByTranslatableTransformer extends ObjectTransformer<Translatable, Component> {

  protected ComponentByTranslatableTransformer() {
    super(Translatable.class);
  }

  @Override
  public Component transform(final Viewer viewer, final Translatable value) {
    return translatable(value.translationKey());
  }
}
