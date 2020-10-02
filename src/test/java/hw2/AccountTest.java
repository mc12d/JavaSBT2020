package hw2;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    @Test
    public void addWithdrawTest() throws InterruptedException {
        TransactionManager tmanager = new TransactionManager();
        Account account = new Account(0, tmanager),
                account1 = new Account(1, tmanager);

        assertFalse(account.withdrawCash(-100));
        assertFalse(account.withdrawCash(100));

        assertTrue(account.addCash(100));
        Thread.sleep(1000);
        assertTrue(account.withdraw(50, account1));

        assertFalse(account1.withdraw(60, account));
        assertFalse(account.withdraw(0, account1));
        assertFalse(account.withdraw(-10, account1));

        assertTrue(account1.withdraw(50, account));
        assertTrue(account.withdrawCash(100));
        assertFalse(account.withdrawCash(1));
    }

    @Test
    public void balanceOnTest() {
        TransactionManager tmanager = new TransactionManager();
        Account account = new Account(0, tmanager);

        account.addCash(100);
        assertEquals(100, account.balanceOn(LocalDate.now()));

        account.withdrawCash(50);
        assertEquals(50, account.balanceOn(LocalDate.now()));
    }
}
