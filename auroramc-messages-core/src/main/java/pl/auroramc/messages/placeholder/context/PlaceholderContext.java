package pl.auroramc.messages.placeholder.context;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderContext {

  private final Map<String, Object> valuesByPaths;

  private PlaceholderContext() {
    this.valuesByPaths = new HashMap<>();
  }

  public static PlaceholderContext newPlaceholderContext() {
    return new PlaceholderContext();
  }

  public PlaceholderContext placeholder(final String path, final Object value) {
    valuesByPaths.put(path, value);
    return this;
  }

  public Object getValueByPath(final String path) {
    return valuesByPaths.get(path);
  }

  public Map<String, Object> getValuesByPaths() {
    return valuesByPaths;
  }
}
