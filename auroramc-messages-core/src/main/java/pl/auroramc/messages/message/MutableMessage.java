package pl.auroramc.messages.message;

import static java.util.Arrays.stream;
import static pl.auroramc.messages.placeholder.context.PlaceholderContext.newPlaceholderContext;

import pl.auroramc.messages.placeholder.context.PlaceholderContext;

public class MutableMessage {

  public static final String LINE_DELIMITER = "<newline>";
  private static final String EMPTY_DELIMITER = "";
  private static final MutableMessage EMPTY_MESSAGE = new MutableMessage(EMPTY_DELIMITER);
  private final String template;
  private final PlaceholderContext placeholderContext;

  MutableMessage(final String template) {
    this.template = template;
    this.placeholderContext = newPlaceholderContext();
  }

  public static MutableMessage of(final String template) {
    return new MutableMessage(template);
  }

  public static MutableMessage empty() {
    return EMPTY_MESSAGE;
  }

  public MutableMessage placeholder(final String path, final Object value) {
    placeholderContext.placeholder(path, value);
    return this;
  }

  public MutableMessage append(final MutableMessage message, final String delimiter) {
    if (isEmpty()) {
      return this;
    }
    return new MutableMessage(template + delimiter + message.template);
  }

  public MutableMessage append(final MutableMessage message) {
    return append(message, LINE_DELIMITER);
  }

  public MutableMessage[] children(final String delimiter) {
    return stream(template.split(delimiter))
        .map(MutableMessage::new)
        .toArray(MutableMessage[]::new);
  }

  public MutableMessage[] children() {
    return children(LINE_DELIMITER);
  }

  public MutableMessageCollector collector() {
    return new MutableMessageCollector();
  }

  public boolean isEmpty() {
    return template.isEmpty();
  }

  public String getTemplate() {
    return template;
  }

  public PlaceholderContext getPlaceholderContext() {
    return placeholderContext;
  }
}
