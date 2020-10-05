package hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {
    @Test
    public void withDrawMoney_balanceIsNotEnough() {
        // given
        Customer customer = new Customer("John", "Doe");
        Account account   = new Account(0);

        // when
        boolean success = account.withdraw(1);

        //then
        assertFalse(success);
    }


    @Test
    public void withdrawMoney_isAmountsCorrect() {
        // given
        Customer customer = new Customer("John", "Doe");
        Account account   = new Account(0);

        // when
        boolean validWithdraws = true, invalidWithdraw = false;
        account.add(100);
        validWithdraws &= account.withdraw(30);
        validWithdraws &= account.withdraw(30);
        validWithdraws &= account.withdraw(40);
        invalidWithdraw = account.withdraw(1);

        // then
        assertTrue(validWithdraws);
        assertFalse(invalidWithdraw);
    }


    @Test
    public void addMoney_valueValidity() {
        //given
        Customer customer = new Customer("John", "Doe");
        Account account   = new Account(0);

        // when
        boolean invalidValue = account.add(0) || account.add(-1);
        boolean validValue   = account.add(100);

        // then
        assertFalse(invalidValue);
        assertTrue(validValue);
    }
}
