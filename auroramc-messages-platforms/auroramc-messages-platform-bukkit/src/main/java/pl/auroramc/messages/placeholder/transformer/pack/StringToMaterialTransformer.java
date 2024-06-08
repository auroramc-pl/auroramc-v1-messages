package pl.auroramc.messages.placeholder.transformer.pack;

import static java.util.Locale.ROOT;
import static org.apache.commons.lang3.StringUtils.capitalize;

import org.bukkit.Material;

class StringToMaterialTransformer extends ObjectTransformer<Material, String> {

  private static final String ENUM_SECTION_DELIMITER = "_";
  private static final String NAME_SECTION_DELIMITER = " ";

  StringToMaterialTransformer() {
    super(Material.class);
  }

  @Override
  public String transform(final Material value) {
    return getFormattedName(value);
  }

  private String getFormattedName(final Material material) {
    return capitalize(
        material.name().toLowerCase(ROOT).replace(ENUM_SECTION_DELIMITER, NAME_SECTION_DELIMITER));
  }
}
