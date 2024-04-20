package pl.auroramc.messages.placeholder.transformer.pack;

import pl.auroramc.messages.message.compiler.MessageCompiler;
import pl.auroramc.messages.placeholder.transformer.registry.ObjectTransformerRegistry;

public class BukkitObjectTransformerPack implements ObjectTransformerPack {

  private final MessageCompiler<?> messageCompiler;

  public BukkitObjectTransformerPack(final MessageCompiler<?> messageCompiler) {
    this.messageCompiler = messageCompiler;
  }

  @Override
  public void register(final ObjectTransformerRegistry transformerRegistry) {
    transformerRegistry.register(new ComponentToItemStackTransformer(messageCompiler));
    transformerRegistry.register(new StringToMaterialTransformer());
  }
}
