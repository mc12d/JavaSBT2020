package hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    @Test
    public void constructorValidity() {
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
        //given
        Customer customer = new Customer("John", "Doe");

        //then
        assertEquals("John Doe", customer.fullName());
    }

    @Test
    public void openCloseAccount() {
        // given
        Customer customer = new Customer("John", "Doe");

        // when
        boolean closeNonExisting = customer.closeAccount();
        boolean openWhenNotExist  = customer.openAccount(0);
        boolean openWhenExist     = customer.openAccount(1);

        // then
        assertFalse(closeNonExisting);
        assertTrue(openWhenNotExist);
        assertFalse(openWhenExist);
    }

    @Test
    public void addWithdraw_withNonExistingAccount() {
        // given
        Customer customer = new Customer("John", "Doe");

        // when
        boolean addSuccess = customer.addMoneyToCurrentAccount(1);
        boolean withdrawSuccess = customer.withdrawFromCurrentAccount(1);

        // then
        assertFalse(addSuccess);
        assertFalse(withdrawSuccess);
    }

    @Test
    public void addWithdraw_withExistingAccount() {
        // given
        Customer customer = new Customer("John", "Doe");
        customer.openAccount(0);

        // when
        boolean addSuccess = customer.addMoneyToCurrentAccount(1);
        boolean withdrawSuccess = customer.withdrawFromCurrentAccount(1);

        // then
        assertTrue(addSuccess);
        assertTrue(withdrawSuccess);
    }
}
