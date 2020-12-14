package hw3;

public interface KeyExtractor<K, V> {
    K extract(V entity);
}
