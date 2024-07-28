package pl.auroramc.messages.viewer;

import com.velocitypowered.api.proxy.Player;
import java.util.Locale;
import net.kyori.adventure.audience.Audience;

public class VelocityViewer extends KyoriViewer {

  VelocityViewer(final Audience audience) {
    super(audience);
  }

  @Override
  public Locale getLocale() {
    if (unwrap() instanceof Player player && player.getEffectiveLocale() != null) {
      return player.getEffectiveLocale();
    }
    return super.getLocale();
  }
}
