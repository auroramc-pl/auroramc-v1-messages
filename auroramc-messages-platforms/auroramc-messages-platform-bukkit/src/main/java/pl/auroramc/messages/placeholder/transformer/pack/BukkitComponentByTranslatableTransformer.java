package pl.auroramc.messages.placeholder.transformer.pack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.inventory.ItemStack;
import pl.auroramc.messages.placeholder.transformer.pack.standard.ComponentByTranslatableTransformer;
import pl.auroramc.messages.viewer.Viewer;

public class BukkitComponentByTranslatableTransformer extends ComponentByTranslatableTransformer {

  @Override
  public Component transform(final Viewer viewer, final Translatable value) {
    Component transformedValue = super.transform(viewer, value);
    if (value instanceof ItemStack itemStack) {
      transformedValue = transformedValue.hoverEvent(itemStack);
    }
    return transformedValue;
  }
}
