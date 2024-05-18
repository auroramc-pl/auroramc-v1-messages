package pl.auroramc.messages.i18n.locale;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import java.util.Locale;

public class VelocityLocaleProvider implements LocaleProvider<CommandSource> {

  private final Locale fallbackLocale;

  public VelocityLocaleProvider(final Locale fallbackLocale) {
    this.fallbackLocale = fallbackLocale;
  }

  @Override
  public Locale getLocale(final CommandSource viewer) {
    if (viewer instanceof Player player && player.getEffectiveLocale() != null) {
      return player.getEffectiveLocale();
    }

    return fallbackLocale;
  }
}
