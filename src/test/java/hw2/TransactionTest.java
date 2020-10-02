package hw2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    @Test
    public void contructorTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Transaction t = new Transaction(
                  0,
                  100,
                  null,
                  null,
                  false,
                  false
            );
        });
    }

    @Test
    public void executeTest() {
        TransactionManager tmanager = new TransactionManager();
        Account acc1 = new Account(0, tmanager),
                acc2 = new Account(1, tmanager);

        Transaction t1 = tmanager.createTransaction(100, null, acc1);
        t1.execute();
        assertNotNull(acc1.entries.last());
        assertEquals(100, acc1.entries.last().getAmount());
    }

    @Test
    public void rollbackTest() {

    }
}
