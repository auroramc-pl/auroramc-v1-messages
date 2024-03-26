package pl.auroramc.messages.placeholder.scanner;

public interface PlaceholderScanner {

  static PlaceholderScanner getPlaceholderScanner() {
    return new PlaceholderScannerImpl();
  }

  String[] getPlaceholderPaths(final String template);

  String getMergedPath(final String parentPath, final String childPath);

  boolean hasPathChildren(final String path);
}
