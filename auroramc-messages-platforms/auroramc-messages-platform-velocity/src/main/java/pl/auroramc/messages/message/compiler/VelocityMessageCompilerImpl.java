package pl.auroramc.messages.message.compiler;

import com.velocitypowered.api.command.CommandSource;
import java.util.concurrent.Executor;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;

class VelocityMessageCompilerImpl extends MessageCompilerImpl<CommandSource>
    implements VelocityMessageCompiler {

  VelocityMessageCompilerImpl(
      final Executor executor, final PlaceholderResolver<CommandSource> placeholderResolver) {
    super(executor, placeholderResolver);
  }
}
