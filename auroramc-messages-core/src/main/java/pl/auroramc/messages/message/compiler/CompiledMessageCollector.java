package pl.auroramc.messages.message.compiler;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static net.kyori.adventure.text.Component.empty;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CompiledMessageCollector
    implements Collector<CompiledMessage, List<CompiledMessage>, CompiledMessage> {

  CompiledMessageCollector() {}

  public static CompiledMessageCollector collector() {
    return new CompiledMessageCollector();
  }

  @Override
  public Supplier<List<CompiledMessage>> supplier() {
    return ArrayList::new;
  }

  @Override
  public BiConsumer<List<CompiledMessage>, CompiledMessage> accumulator() {
    return List::add;
  }

  @Override
  public BinaryOperator<List<CompiledMessage>> combiner() {
    return (reducer, elements) -> {
      reducer.addAll(elements);
      return reducer;
    };
  }

  @Override
  public Function<List<CompiledMessage>, CompiledMessage> finisher() {
    return components ->
        new CompiledMessage(
            components.stream()
                .map(CompiledMessage::getComponent)
                .reduce(empty(), CompiledMessageCollectorUtils::appendComponent));
  }

  @Override
  public Set<Characteristics> characteristics() {
    return unmodifiableSet(EnumSet.of(CONCURRENT));
  }
}
