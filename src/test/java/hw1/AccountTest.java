package hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {
    @Test
    public void withdrawTest() {
        Customer customer = new Customer("John", "Doe");
        Account account   = new Account(0);

        account.add(90);

        assertTrue(account.withdraw(80));
        assertFalse(account.withdraw(20));
        assertTrue(account.withdraw(10));
    }

    @Test
    public void addTest() {
        Customer customer = new Customer("John", "Doe");
        Account account   = new Account(0);

        assertFalse(account.add(0));
        assertTrue(account.add(100));
        assertFalse(account.add(-1));
    }
}
