package hw2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class EntriesTest {
    @Test
    public void addLastEntryTest() {
        Entries entries = new Entries();
        entries.addEntry(new Entry(
                null, null, 0, LocalDateTime.now()
        ));
        Entry ent = new Entry(
                null,
                null,
                1,
                LocalDateTime.of(2077, 12, 1, 0, 0, 1)
        );
        entries.addEntry(ent);
        assertEquals(ent, entries.last());
    }

    @Test
    public void betweenDatesTest() {
        Entries entriesLarge = new Entries();
        TreeSet<Entry> entriesBetween = new TreeSet<>();
        TreeSet<Entry> entriesFrom = new TreeSet<>();
        TreeSet<Entry> entriesTo = new TreeSet<>();

        for (int i = 1; i < 10; i++) {
            Entry e = new Entry(
                    null,
                    null,
                    i,
                    LocalDateTime.of(2077, 12, i, 0, 0, 30)
            );
            if (i >= 2 && i <= 5) {
                entriesBetween.add(e);
            }
            if (i <= 5) {
                entriesTo.add(e);
            }
            if (i >= 5) {
                entriesFrom.add(e);
            }
            entriesLarge.addEntry(e);
        }

        assertEquals(entriesBetween, entriesLarge.betweenDates(
                LocalDate.of(2077, 12, 2),
                LocalDate.of(2077, 12, 5)
        ));
        assertEquals(entriesFrom, entriesLarge.from(
                LocalDate.of(2077, 12, 5)
        ));
        assertEquals(entriesTo, entriesLarge.to(
                LocalDate.of(2077, 12, 5)
        ));
    }
}
