package pl.auroramc.messages.message;

import static java.util.Arrays.stream;
import static pl.auroramc.messages.placeholder.context.PlaceholderContext.newPlaceholderContext;

import pl.auroramc.messages.placeholder.context.PlaceholderContext;

public class MutableMessage {

  public static final String LINE_DELIMITER = "<newline>";
  private static final String EMPTY_DELIMITER = "";
  private static final MutableMessage EMPTY_MESSAGE = of(EMPTY_DELIMITER);
  private final String template;
  private final PlaceholderContext context;

  MutableMessage(final String template, final PlaceholderContext context) {
    this.template = template;
    this.context = context;
  }

  public static MutableMessageCollector collector() {
    return new MutableMessageCollector();
  }

  public static MutableMessage of(final String template) {
    return new MutableMessage(template, newPlaceholderContext());
  }

  public static MutableMessage empty() {
    return EMPTY_MESSAGE;
  }

  public MutableMessage placeholder(final String path, final Object value) {
    return new MutableMessage(template, context.placeholder(path, value));
  }

  public MutableMessage append(final MutableMessage message, final String delimiter) {
    if (isEmpty()) {
      return this;
    }

    return new MutableMessage(template + delimiter + message.template, context);
  }

  public MutableMessage append(final MutableMessage message) {
    return append(message, LINE_DELIMITER);
  }

  public MutableMessage[] children(final String delimiter) {
    return stream(template.split(delimiter))
        .map(child -> new MutableMessage(child, context))
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

  public PlaceholderContext getContext() {
    return context;
  }
}
