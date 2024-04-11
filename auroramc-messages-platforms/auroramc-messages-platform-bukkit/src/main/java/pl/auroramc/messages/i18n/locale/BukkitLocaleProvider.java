package pl.auroramc.messages.i18n.locale;

import java.util.Locale;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitLocaleProvider implements LocaleProvider<CommandSender> {

  private final Locale fallbackLocale;

  public BukkitLocaleProvider(final Locale fallbackLocale) {
    this.fallbackLocale = fallbackLocale;
  }

  @Override
  public Locale getLocale(final CommandSender source) {
    if (source instanceof Player player) {
      return player.locale();
    }

    return fallbackLocale;
  }
}
