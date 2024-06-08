package pl.auroramc.messages.placeholder.transformer.pack;

import static pl.auroramc.messages.placeholder.transformer.pack.ComponentToItemStackTransformerUtils.getFormattedItemStack;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import pl.auroramc.messages.message.compiler.MessageCompiler;

class ComponentToItemStackTransformer extends ObjectTransformer<ItemStack, Component> {

  private final MessageCompiler<?> messageCompiler;

  ComponentToItemStackTransformer(final MessageCompiler<?> messageCompiler) {
    super(ItemStack.class);
    this.messageCompiler = messageCompiler;
  }

  @Override
  public Component transform(final ItemStack itemStack) {
    return getFormattedItemStack(messageCompiler, itemStack);
  }
}
