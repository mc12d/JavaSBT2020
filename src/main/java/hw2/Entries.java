package hw2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
public class Entries {
    private final NavigableSet<Entry> entData;

    public Entries() {
        entData = new TreeSet<>();
    }

    void addEntry(Entry entry) {
        entData.add(entry);
    }

    Collection<Entry> from(LocalDate date) {
        LocalDateTime dateBegin = LocalDateTime.of(date, LocalTime.MIN);
        return entData.tailSet(new Entry(null, null, 0, dateBegin), true);
    }

    Collection<Entry> to(LocalDate date) {
        LocalDateTime dateEnd = LocalDateTime.of(date, LocalTime.MAX);
        return entData.headSet(new Entry(null, null, 0, dateEnd), true);
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        LocalDateTime dateBegin = LocalDateTime.of(from, LocalTime.MIN),
                      dateEnd   = LocalDateTime.of(to, LocalTime.MAX);
        return entData.subSet(
                new Entry(null, null, 0, dateBegin), true,
                new Entry(null, null, 0, dateEnd),   true
        );

    }

    Entry last() {
        return entData.pollLast();
    }
}
