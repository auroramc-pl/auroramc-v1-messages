package pl.auroramc.messages.message.compiler;

import java.util.concurrent.Executor;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;

class VelocityMessageCompilerImpl extends MessageCompilerImpl implements VelocityMessageCompiler {

  VelocityMessageCompilerImpl(
      final Executor executor, final PlaceholderResolver placeholderResolver) {
    super(executor, placeholderResolver);
  }
}
