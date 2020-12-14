package hw2;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntriesTest {
    @Test
    public void addLastEntryTest() {
        // given
        Entries entries = new Entries();
        Entry ent = new Entry(
                null,
                null,
                1,
                LocalDateTime.of(2077, 12, 1, 0, 0, 1)
        );

        // when
        entries.addEntry(ent);

        // then
        assertEquals(ent, entries.last());
    }

    @Test
    public void betweenDatesTest() {
        // given
        Entries entriesLarge = new Entries();
        Collection<Entry> entriesBetween = new TreeSet<>();
        Collection<Entry> entriesFrom = new TreeSet<>();
        Collection<Entry> entriesTo = new TreeSet<>();

        for (int i = 1; i < 10; i++) {
            Entry e = new Entry(
                    null,
                    null,
                    i,
                    LocalDateTime.of(2077, 12, i, 0, 0, 30)
            );
            // filling entriesLarge collection and two its subsets
            entriesLarge.addEntry(e);
            if (i >= 2 && i <= 5) {
                entriesBetween.add(e);
            }
            if (i <= 5) {
                entriesTo.add(e);
            }
            if (i >= 5) {
                entriesFrom.add(e);
            }
        }

        // when
        Collection<Entry> entriesBetweenActual = entriesLarge.betweenDates(
                LocalDate.of(2077, 12, 2),
                LocalDate.of(2077, 12, 5)
        );
        Collection<Entry> entriesFromActual =  entriesLarge.from(
                LocalDate.of(2077, 12, 5)
        );
        Collection<Entry> entriesToActual = entriesLarge.to(
                LocalDate.of(2077, 12, 5)
        );

        // then
        assertEquals(entriesBetween, entriesBetweenActual);
        assertEquals(entriesFrom, entriesFromActual);
        assertEquals(entriesTo, entriesToActual);
    }
}
