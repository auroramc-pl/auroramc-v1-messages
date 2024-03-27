package pl.auroramc.messages.placeholder.reflect;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.toUpperCase;

import java.util.Map;

class ReflectivePlaceholderEvaluatorUtils {

  private static final Map<Character, String> PATH_TOKENS = Map.of('@', "get");

  ReflectivePlaceholderEvaluatorUtils() {}

  static String resolveTokens(final String in) {
    final StringBuilder out = new StringBuilder();

    int index = 0;
    while (index < in.length()) {
      final char character = in.charAt(index++);
      if (PATH_TOKENS.containsKey(character)) {
        out.append(PATH_TOKENS.get(character));
        if (index < in.length() && isLowerCase(in.charAt(index))) {
          out.append(toUpperCase(in.charAt(index++)));
        }
      } else {
        out.append(character);
      }
    }

    return out.toString();
  }
}
