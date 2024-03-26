package pl.auroramc.messages.placeholder.scanner;

public interface PlaceholderScanner {

  static PlaceholderScanner getPlaceholderScanner() {
    return new PlaceholderScannerImpl();
  }

  String[] getPlaceholderPaths(final String template);

  String getMergedPaths(final String parentPath, final String childPath);

  boolean hasPathChildren(final String path);
}
