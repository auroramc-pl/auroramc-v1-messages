package pl.auroramc.messages.viewer;

import java.util.Locale;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;

public class BukkitViewer extends KyoriViewer {

  BukkitViewer(final Audience audience) {
    super(audience);
  }

  @Override
  public Locale getLocale() {
    if (unwrap() instanceof Player player) {
      return player.locale();
    }
    return super.getLocale();
  }
}
