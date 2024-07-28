package pl.auroramc.messages.message;

import static java.util.Arrays.stream;
import static pl.auroramc.commons.format.StringUtils.BLANK;
import static pl.auroramc.messages.message.property.MessageProperty.getMessageProperty;

import java.util.Map;
import pl.auroramc.messages.message.property.MessageProperty;

public class MutableMessage {

  public static final String LINE_DELIMITER = "<newline>";
  private static final MutableMessage EMPTY_MESSAGE = of(BLANK);
  private static final MutableMessage NEWLINE_MESSAGE = of(LINE_DELIMITER);
  private final String template;
  private final MessageProperty property;

  MutableMessage(final String template, final MessageProperty property) {
    this.template = template;
    this.property = property;
  }

  public static MutableMessage of(final String template) {
    return new MutableMessage(template, getMessageProperty());
  }

  public static MutableMessage of(final String template, final MessageProperty property) {
    return new MutableMessage(template, property);
  }

  public static MutableMessage empty() {
    return EMPTY_MESSAGE;
  }

  public static MutableMessage newline() {
    return NEWLINE_MESSAGE;
  }

  public MutableMessage placeholder(final String path, final Object value) {
    return new MutableMessage(template, property.placeholder(path, value));
  }

  public MutableMessage placeholders(final Map<String, Object> placeholders) {
    return new MutableMessage(template, property.placeholders(placeholders));
  }

  public MutableMessage mapping(final String path, final String target) {
    return new MutableMessage(template, property.mapping(path, target));
  }

  public MutableMessage append(final MutableMessage message, final String delimiter) {
    if (isEmpty()) {
      return message;
    }

    return new MutableMessage(template + delimiter + message.template, property);
  }

  public MutableMessage append(final MutableMessage message) {
    return append(message, LINE_DELIMITER);
  }

  public MutableMessage[] children(final String delimiter) {
    return stream(template.split(delimiter))
        .map(child -> new MutableMessage(child, property))
        .toArray(MutableMessage[]::new);
  }

  public MutableMessage[] children() {
    return children(LINE_DELIMITER);
  }

  public boolean isEmpty() {
    return template.isEmpty();
  }

  public String getTemplate() {
    return template;
  }

  public MessageProperty getProperty() {
    return property;
  }
}
