package pl.auroramc.messages.message.compiler;

import java.util.concurrent.Executor;
import org.bukkit.command.CommandSender;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;

class BukkitMessageCompilerImpl extends MessageCompilerImpl<CommandSender>
    implements BukkitMessageCompiler {

  BukkitMessageCompilerImpl(
      final Executor executor,
      final PlaceholderScanner placeholderScanner,
      final PlaceholderResolver<CommandSender> placeholderResolver) {
    super(executor, placeholderScanner, placeholderResolver);
  }
}
