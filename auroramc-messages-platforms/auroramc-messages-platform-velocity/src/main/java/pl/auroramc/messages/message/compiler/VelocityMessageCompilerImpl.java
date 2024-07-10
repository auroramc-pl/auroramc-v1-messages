package pl.auroramc.messages.message.compiler;

import com.velocitypowered.api.command.CommandSource;
import java.util.concurrent.Executor;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;

class VelocityMessageCompilerImpl extends MessageCompilerImpl<CommandSource>
    implements VelocityMessageCompiler {

  VelocityMessageCompilerImpl(
      final Executor executor,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderResolver<CommandSource> placeholderResolver) {
    super(executor, placeholderScanner, placeholderResolver);
  }
}
