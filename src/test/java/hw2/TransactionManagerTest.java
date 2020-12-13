package hw2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionManagerTest {
    @Test
    public void createTransactionTest() {
        TransactionManager tmanager = new TransactionManager();

        assertThrows(IllegalArgumentException.class, () -> {
            tmanager.createTransaction(
                    100, null, null
            );
        });
    }

    @Test
    public void findByAccountTest() {
        TransactionManager tmanager = new TransactionManager();
        ArrayList<Transaction> groundTrue = new ArrayList<>();

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Account(0, tmanager));
        accounts.add(new Account(1, tmanager));
        accounts.add(new Account(2, tmanager));

        for (int i = 0; i < 10; i++) {
            Transaction t = tmanager.createTransaction(100 + i, null, accounts.get(i % 3));
            if (i % 3 == 0) {
                groundTrue.add(t);
            }
        }
        assertEquals(groundTrue, tmanager.findAllTransactionsByAccount(
                accounts.get(0)
        ));
    }

    @Test
    public void executeTest() {
        // given
        TransactionManager tmanager = new TransactionManager();
        Account acc1 = new Account(0, tmanager);
        Transaction t1 = tmanager.createTransaction(100, null, acc1);
        assertNotNull(t1);
        assertNotNull(tmanager);
        // when
        tmanager.executeTransaction(t1);

        // then
        assertNotNull(acc1.entries.last());
        assertEquals(100, acc1.entries.last().getAmount());
    }
}
