package pl.auroramc.messages.message.component;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import net.kyori.adventure.text.Component;

public class ComponentCollector implements Collector<Component, List<Component>, Component> {

  ComponentCollector() {}

  public static ComponentCollector collector() {
    return new ComponentCollector();
  }

  @Override
  public Supplier<List<Component>> supplier() {
    return ArrayList::new;
  }

  @Override
  public BiConsumer<List<Component>, Component> accumulator() {
    return List::add;
  }

  @Override
  public BinaryOperator<List<Component>> combiner() {
    return (reducer, elements) -> {
      reducer.addAll(elements);
      return reducer;
    };
  }

  @Override
  public Function<List<Component>, Component> finisher() {
    return components -> components.stream().reduce(Component.empty(), this::append);
  }

  @Override
  public Set<Characteristics> characteristics() {
    return unmodifiableSet(EnumSet.of(CONCURRENT));
  }

  private Component append(final Component aggregator, final Component component) {
    return isEmpty(aggregator) ? component : aggregator.appendNewline().append(component);
  }

  private boolean isEmpty(final Component component) {
    return miniMessage().serialize(component).isEmpty();
  }
}
