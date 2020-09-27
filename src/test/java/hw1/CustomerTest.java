package hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    @Test
    public void Customer() {
        assertThrows(IllegalArgumentException.class, () -> {
            Customer customer = new Customer(null, "Doe");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Customer customer = new Customer("John", "");
        });

        Customer customer = new Customer("John", "Doe");
    }

    @Test
    public void fullName() {
        Customer customer = new Customer("John", "Doe");
        assertTrue("John Doe".equals(customer.fullName()));
    }

    @Test
    public void accountOpenClose() {
        Customer customer = new Customer("John", "Doe");

        assertFalse(customer.closeAccount());
        assertTrue(customer.openAccount(0));
        assertFalse(customer.openAccount(1));
        assertTrue(customer.closeAccount());
        assertFalse(customer.closeAccount());
    }

    @Test
    public void accountAddWithdraw() {
        Customer customer = new Customer("John", "Doe");

        assertFalse(customer.addMoneyToCurrentAccount(1));
        assertFalse(customer.addMoneyToCurrentAccount(1));

        customer.openAccount(0);
        assertTrue(customer.addMoneyToCurrentAccount(1));
        assertTrue(customer.withdrawFromCurrentAccount(1));

        assertFalse(customer.addMoneyToCurrentAccount(0));
        assertFalse(customer.addMoneyToCurrentAccount(-1));
        assertTrue(customer.addMoneyToCurrentAccount(1));

        assertFalse(customer.withdrawFromCurrentAccount(2));
        assertTrue(customer.withdrawFromCurrentAccount(1));
        assertFalse(customer.withdrawFromCurrentAccount(-1));
    }
}
