package pl.auroramc.messages.placeholder.transformer.pack;

import static java.util.Locale.ROOT;
import static org.apache.commons.lang3.StringUtils.capitalize;

import org.bukkit.Material;

class StringToMaterialTransformer implements ObjectTransformer<Material, String> {

  private static final String ENUM_SECTION_DELIMITER = "_";
  private static final String NAME_SECTION_DELIMITER = " ";

  StringToMaterialTransformer() {}

  @Override
  public String transform(final Material value) {
    return getPrettyName(value);
  }

  @Override
  public Class<?> type() {
    return Material.class;
  }

  private String getPrettyName(final Material material) {
    return capitalize(
        material.name().toLowerCase(ROOT).replace(ENUM_SECTION_DELIMITER, NAME_SECTION_DELIMITER));
  }
}
