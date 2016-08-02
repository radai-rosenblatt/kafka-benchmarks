package net.radai.kafka;

public interface Metric<E> {
  long measure(E instance);
}
