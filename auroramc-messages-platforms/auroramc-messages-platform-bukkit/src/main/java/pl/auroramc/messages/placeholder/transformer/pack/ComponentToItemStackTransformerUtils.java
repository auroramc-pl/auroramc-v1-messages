package pl.auroramc.messages.placeholder.transformer.pack;

import static net.kyori.adventure.text.Component.translatable;

import java.util.Optional;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.compiler.MessageCompiler;

final class ComponentToItemStackTransformerUtils {

  private static final String ITEM_STACK_DISPLAY_NAME_PATH = "displayName";
  private static final String ITEM_STACK_AMOUNT_PATH = "amount";
  private static final MutableMessage ITEM_STACK_TEMPLATE =
      MutableMessage.of("<dark_gray>x{amount} <white>{displayName}");

  private ComponentToItemStackTransformerUtils() {}

  static Component getFormattedItemStack(
      final MessageCompiler<?> messageCompiler, final ItemStack itemStack) {
    return messageCompiler
        .compile(
            null,
            ITEM_STACK_TEMPLATE
                .placeholder(ITEM_STACK_AMOUNT_PATH, itemStack.getAmount())
                .placeholder(ITEM_STACK_DISPLAY_NAME_PATH, displayName(itemStack)))
        .getComponent()
        .hoverEvent(itemStack.asHoverEvent());
  }

  private static Component displayName(final ItemStack itemStack) {
    return Optional.ofNullable(itemStack.getItemMeta())
        .map(ItemMeta::displayName)
        .orElse(translatable(itemStack));
  }
}
