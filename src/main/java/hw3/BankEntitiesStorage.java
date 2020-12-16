package hw3;

import java.util.List;

public interface BankEntitiesStorage<K, V> {
    void save(V entity);
    void saveAll(List<? extends V> entities);
    V findByKey(K key);
    List<? extends V> findAll();
    void deleteByKey(K key);
    void deleteAll(List<? extends V> entities);
}
