package pl.auroramc.messages.message.property;

import java.util.HashMap;
import java.util.Map;

public class MessageProperty {

  private final Map<String, Object> valuesByPaths;
  private final Map<String, String> targetByPath;

  private MessageProperty(
      final Map<String, Object> valuesByPaths, final Map<String, String> targetByPath) {
    this.valuesByPaths = valuesByPaths;
    this.targetByPath = targetByPath;
  }

  public static MessageProperty getMessageProperty() {
    return new MessageProperty(new HashMap<>(), new HashMap<>());
  }

  public MessageProperty placeholder(final String path, final Object value) {
    final Map<String, Object> copyOfValuesByPaths = new HashMap<>(this.valuesByPaths);
    copyOfValuesByPaths.put(path, value);
    return new MessageProperty(copyOfValuesByPaths, targetByPath);
  }

  public MessageProperty placeholders(final Map<String, Object> placeholders) {
    final Map<String, Object> copyOfValuesByPaths = new HashMap<>(this.valuesByPaths);
    copyOfValuesByPaths.putAll(placeholders);
    return new MessageProperty(copyOfValuesByPaths, targetByPath);
  }

  public MessageProperty mapping(final String path, final String target) {
    targetByPath.put(path, target);
    return this;
  }

  public Object getValueByPath(final String path) {
    return valuesByPaths.get(path);
  }

  public String getTargetByPath(final String path) {
    return targetByPath.getOrDefault(path, path);
  }

  public Map<String, Object> getValuesByPaths() {
    return valuesByPaths;
  }

  public Map<String, String> getTargetByPath() {
    return targetByPath;
  }
}
