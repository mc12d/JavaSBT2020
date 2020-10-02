package hw2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyticsManagerTest {

    @Test
    public void mostFrequentBeneficiaryTest() {
        TransactionManager tmanager = new TransactionManager();
        AnalyticsManager manager = new AnalyticsManager(tmanager);

        ArrayList<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            accounts.add(new Account(i, tmanager));
        }

        Account originator = accounts.get(0);
        originator.addCash(10000);

        originator.withdraw(100, accounts.get(1));
        originator.withdraw(100, accounts.get(1));
        originator.withdraw(100, accounts.get(2));
        originator.withdraw(100, accounts.get(2));
        originator.withdraw(100, accounts.get(2));
        originator.withdraw(100, accounts.get(8));
        originator.withdraw(100, accounts.get(10));

        assertEquals(
                accounts.get(2),
                manager.mostFrequentBeneficiaryOfAccount(originator)
        );
    }

    @Test
    public void topExpensivePurchasesTest() {
        TransactionManager tmanager = new TransactionManager();
        AnalyticsManager manager = new AnalyticsManager(tmanager);

        Account account = new Account(0, tmanager);
        Account beneficiary = new Account(1, tmanager);

        assertThrows(IllegalArgumentException.class, () -> {
            manager.topExpensivePurchases(account, -1);
        });

        account.addCash(10000);
        account.withdraw(100, beneficiary);
        account.withdraw(200, beneficiary);
        account.withdraw(300, beneficiary);
        account.withdraw(400, beneficiary);
        account.withdraw(500, beneficiary);
        account.withdraw(600, beneficiary);

        Iterator<Transaction> iterator = manager.topExpensivePurchases(account, 3).iterator();
        assertEquals(
                600,
                iterator.next().getAmount()
        );
        assertEquals(
                500,
                iterator.next().getAmount()
        );
    }


}
