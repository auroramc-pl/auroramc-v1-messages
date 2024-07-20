package pl.auroramc.messages.placeholder.transformer.pack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.inventory.ItemStack;
import pl.auroramc.messages.placeholder.transformer.pack.standard.ComponentByTranslatableTransformer;

public class BukkitComponentByTranslatableTransformer extends ComponentByTranslatableTransformer {

  @Override
  public Component transform(final Translatable value) {
    Component transformedValue = super.transform(value);
    if (value instanceof ItemStack itemStack) {
      transformedValue = transformedValue.hoverEvent(itemStack);
    }
    return transformedValue;
  }
}
