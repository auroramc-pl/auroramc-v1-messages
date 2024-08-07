package pl.auroramc.messages.viewer;

import java.util.Locale;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;

public class BukkitViewer extends KyoriViewer {

  protected BukkitViewer(final Audience audience) {
    super(audience);
  }

  @Override
  public Locale getLocale() {
    final Locale locale = getCurrentLocale();
    if (locale != null) {
      return locale;
    }

    if (unwrap() instanceof Player player) {
      return player.locale();
    }

    return super.getLocale();
  }
}
