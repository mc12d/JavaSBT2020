package hw2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionManagerTest {
    @Test
    public void createTransactionTest() {
        // given
        TransactionManager tmanager = new TransactionManager();

        // then
        // exception must be thrown when both accounts in transaction is null
        assertThrows(IllegalArgumentException.class, () -> {
            tmanager.createTransaction(
                    100, null, null
            );
        });
    }

    @Test
    public void findByAccountTest() {
        // given
        TransactionManager tmanager = new TransactionManager();
        ArrayList<Transaction> filteredAccounts = new ArrayList<>();

        ArrayList<DebitCard> accounts = new ArrayList<>();
        accounts.add(new DebitCard(0, tmanager));
        accounts.add(new DebitCard(1, tmanager));
        accounts.add(new DebitCard(2, tmanager));

        // manually filling filteredAccounts with transactions with account id 0
        // and creating transactions with other account ids
        for (int i = 0; i < 10; i++) {
            Transaction t = tmanager.createTransaction(100 + i, null, accounts.get(i % 3));
            if (i % 3 == 0) {
                filteredAccounts.add(t);
            }
        }

        // when
        var filteredAccountsActual = tmanager.findAllTransactionsByAccount(
                accounts.get(0)
        );

        // then
        assertEquals(filteredAccounts, filteredAccountsActual);
    }

    @Test
    public void executeTest() {
        // given
        TransactionManager tmanager = new TransactionManager();
        DebitCard acc1 = new DebitCard(0, tmanager);
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
