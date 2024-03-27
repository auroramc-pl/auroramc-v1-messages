package pl.auroramc.messages.placeholder.reflect;

class PlaceholderEvaluationException extends IllegalArgumentException {

  PlaceholderEvaluationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  PlaceholderEvaluationException(final String message) {
    super(message);
  }
}
