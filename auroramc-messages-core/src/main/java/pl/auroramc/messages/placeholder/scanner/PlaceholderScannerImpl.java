package pl.auroramc.messages.placeholder.scanner;

import static pl.auroramc.messages.placeholder.scanner.PlaceholderScannerUtils.PATH_CHILDREN_DELIMITER;

import java.util.regex.Pattern;

class PlaceholderScannerImpl implements PlaceholderScanner {

  private static final String PLACEHOLDER_REGEX = "\\{([\\w@]+(?:\\.[\\w@]+)*+)}";
  private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile(PLACEHOLDER_REGEX);

  PlaceholderScannerImpl() {}

  @Override
  public String[] getPlaceholderPaths(final String template) {
    return PLACEHOLDER_PATTERN
        .matcher(template)
        .results()
        .map(matchResult -> matchResult.group(1))
        .toArray(String[]::new);
  }

  @Override
  public boolean hasPathChildren(final String path) {
    return path.contains(PATH_CHILDREN_DELIMITER);
  }
}
