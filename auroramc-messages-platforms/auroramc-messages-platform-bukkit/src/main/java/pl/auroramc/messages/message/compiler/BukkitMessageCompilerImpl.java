package pl.auroramc.messages.message.compiler;

import java.util.concurrent.Executor;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;

class BukkitMessageCompilerImpl extends MessageCompilerImpl implements BukkitMessageCompiler {

  BukkitMessageCompilerImpl(
      final Executor executor, final PlaceholderResolver placeholderResolver) {
    super(executor, placeholderResolver);
  }
}
