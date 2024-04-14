package pl.auroramc.messages.placeholder.transformer.pack.standard;

import net.kyori.adventure.text.Component;
import pl.auroramc.messages.message.compiler.CompiledMessage;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;

class ComponentByCompiledMessageTransformer
    implements ObjectTransformer<CompiledMessage, Component> {

  ComponentByCompiledMessageTransformer() {}

  @Override
  public Component transform(final CompiledMessage value) {
    return value.getComponent();
  }

  @Override
  public Class<?> type() {
    return CompiledMessage.class;
  }
}
