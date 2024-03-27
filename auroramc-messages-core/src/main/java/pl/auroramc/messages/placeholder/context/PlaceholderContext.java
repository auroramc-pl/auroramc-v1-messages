package pl.auroramc.messages.placeholder.context;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderContext {

  private final Map<String, Object> valuesByPaths;

  private PlaceholderContext(final Map<String, Object> valuesByPaths) {
    this.valuesByPaths = valuesByPaths;
  }

  public static PlaceholderContext newPlaceholderContext() {
    return new PlaceholderContext(new HashMap<>());
  }

  public PlaceholderContext placeholder(final String path, final Object value) {
    final Map<String, Object> copyOfValuesByPaths = new HashMap<>(this.valuesByPaths);
    copyOfValuesByPaths.put(path, value);
    return new PlaceholderContext(copyOfValuesByPaths);
  }

  public Object getValueByPath(final String path) {
    return valuesByPaths.get(path);
  }

  public Map<String, Object> getValuesByPaths() {
    return valuesByPaths;
  }
}
