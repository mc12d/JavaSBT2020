package hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleEntitiesStorage<K, V> implements BankEntitiesStorage<K, V> {
    private final Map<K, V> storage = new HashMap<>();
    private final KeyExtractor<K, V> keyExtractor;

    public SimpleEntitiesStorage(KeyExtractor<K, V> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    @Override
    public void save(V entity) {
        storage.put(keyExtractor.extract(entity), entity);
    }

    @Override
    public void saveAll(List<? extends V> entities) {
        for (var entity : entities) {
            save(entity);
        }
    }

    @Override
    public V findByKey(K key) {
        return storage.get(key);
    }

    @Override
    public void deleteByKey(K key) {
        storage.remove(key);
    }

    @Override
    public void deleteAll(List<? extends V> entities) {
        for (var entity : entities) {
            deleteByKey(keyExtractor.extract(entity));
        }
    }

    @Override
    public List<? extends V> findAll() {
        return storage.keySet().stream()
                .map(storage::get)
                .collect(Collectors.toList());
    }
}
