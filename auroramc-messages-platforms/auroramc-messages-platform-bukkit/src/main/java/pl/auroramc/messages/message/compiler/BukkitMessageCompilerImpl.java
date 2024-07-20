package pl.auroramc.messages.message.compiler;

import java.util.concurrent.Executor;
import org.bukkit.command.CommandSender;
import pl.auroramc.messages.placeholder.resolver.PlaceholderResolver;

class BukkitMessageCompilerImpl extends MessageCompilerImpl<CommandSender>
    implements BukkitMessageCompiler {

  BukkitMessageCompilerImpl(
      final Executor executor, final PlaceholderResolver<CommandSender> placeholderResolver) {
    super(executor, placeholderResolver);
  }
}
