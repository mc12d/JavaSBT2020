package hw2;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    @Test
    public void addWithdrawTest() throws InterruptedException {
        // given
        TransactionManager tmanager = new TransactionManager();
        Account account = new Account(0, tmanager),
                account1 = new Account(1, tmanager);

        {
            // when
            boolean success1 = account.withdrawCash(-100),
                    success2 = account.withdrawCash(100);

            // then
            assertFalse(success1);
            assertFalse(success2);
        }

        {
            // when
            boolean cashAdded = account.addCash(100);
            boolean cashWithdrawn = account.withdraw(60, account1);

            // then
            assertTrue(cashAdded);
            assertTrue(cashWithdrawn);
            Thread.sleep(10);
            assertEquals(40, account.balanceOn(LocalDate.now()));
            assertEquals(60, account1.balanceOn(LocalDate.now()));
        }

        {
            // account cash is 40
            // account1 cash is 60
            // when
            boolean cashWithdrawn = account.withdraw(60, account1);
            Thread.sleep(10);

            // then
            assertFalse(cashWithdrawn);
            assertEquals(40, account.balanceOn(LocalDate.now()));
            assertEquals(60, account1.balanceOn(LocalDate.now()));
        }


    }

    @Test
    public void balanceOnTest() {
        // given
        TransactionManager tmanager = new TransactionManager();
        Account account = new Account(0, tmanager);

        // when
        account.addCash(100);

        // then
        assertEquals(100, account.balanceOn(LocalDate.now()));

        // when
        account.withdrawCash(50);

        // then
        assertEquals(50, account.balanceOn(LocalDate.now()));
    }
}
