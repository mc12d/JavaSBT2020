package hw2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnalyticsManagerTest {

    @Test
    public void mostFrequentBeneficiaryTest() {
        // given
        TransactionManager tmanager = new TransactionManager();
        AnalyticsManager manager = new AnalyticsManager(tmanager);

        ArrayList<DebitCard> accounts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            accounts.add(new DebitCard(i, tmanager));
        }
        DebitCard originator = accounts.get(0);
        originator.addCash(10000);

        // when
        originator.withdraw(100, accounts.get(1));
        originator.withdraw(100, accounts.get(1));
        originator.withdraw(100, accounts.get(2));
        originator.withdraw(100, accounts.get(2));
        originator.withdraw(100, accounts.get(2));
        originator.withdraw(100, accounts.get(8));
        originator.withdraw(100, accounts.get(9));

        // then
        DebitCard mostFrequentAccount = accounts.get(2);
        assertEquals(
                accounts.get(2),
                manager.mostFrequentBeneficiaryOfAccount(originator)
        );
    }

    @Test
    public void topExpensivePurchasesTest() {
        // given
        TransactionManager tmanager = new TransactionManager();
        AnalyticsManager manager = new AnalyticsManager(tmanager);

        DebitCard account = new DebitCard(0, tmanager);
        DebitCard beneficiary = new DebitCard(1, tmanager);

        account.addCash(10000);
        account.withdraw(100, beneficiary);
        account.withdraw(200, beneficiary);
        account.withdraw(300, beneficiary);
        account.withdraw(400, beneficiary);
        account.withdraw(500, beneficiary);
        account.withdraw(600, beneficiary);

        // when
        int topN1 = -1;

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            manager.topExpensivePurchases(account, topN1);
        });

        // when
        int topN2 = 3;

        //then
        Iterator<Transaction> iterator = manager.topExpensivePurchases(account, topN2).iterator();
        assertEquals(
                600,
                iterator.next().getAmount()
        );
        assertEquals(
                500,
                iterator.next().getAmount()
        );
        assertEquals(
                400,
                iterator.next().getAmount()
        );
    }
}
