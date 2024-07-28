package pl.auroramc.messages.placeholder.transformer.pack.standard;

import net.kyori.adventure.text.Component;
import pl.auroramc.messages.message.compiler.CompiledMessage;
import pl.auroramc.messages.placeholder.transformer.pack.ObjectTransformer;
import pl.auroramc.messages.viewer.Viewer;

class ComponentByCompiledMessageTransformer extends ObjectTransformer<CompiledMessage, Component> {

  ComponentByCompiledMessageTransformer() {
    super(CompiledMessage.class);
  }

  @Override
  public Component transform(final Viewer viewer, final CompiledMessage value) {
    return value.getComponent();
  }
}
