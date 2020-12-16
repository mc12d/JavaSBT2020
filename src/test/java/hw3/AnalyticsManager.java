package hw3;

import hw2.DebitCard;
import hw2.TransactionManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AnalyticsManager {
    @Test
    public void uniqueKeysOf() {
        // given
        var keyExtractor = new DebitCardKeyExtractor();
        var tManager = new TransactionManager();
        var accounts = List.of(
                new DebitCard(0, tManager),
                new DebitCard(1, tManager),
                new DebitCard(2, tManager),
                new DebitCard(1, tManager)
        );
        var analyticsManager = new hw2.AnalyticsManager(tManager);

        // when
        var keySet = analyticsManager.uniqueKeysOf(accounts, keyExtractor);

        // then
        assertEquals(List.of(0L, 1L, 2L), new ArrayList<>(keySet));
    }

    @Test
    public void overallBalanceOfAccounts() {
        // given
        var tManager = new TransactionManager();
        var accounts = List.of(
                new DebitCard(0, tManager),
                new DebitCard(1, tManager),
                new DebitCard(2, tManager)
        );
        accounts.get(0).addCash(100);
        accounts.get(1).addCash(200);
        accounts.get(2).addCash(300);
        var analyticsManager = new hw2.AnalyticsManager(tManager);

        // when
        double sum = analyticsManager.overallBalanceOfAccounts(accounts);

        // then
        assertEquals(600, sum);
    }

    @Test
    public void accountsRangeFrom() {
        // given
        var tManager = new TransactionManager();
        var accounts = List.of(
                new DebitCard(0, tManager),
                new DebitCard(1, tManager),
                new DebitCard(2, tManager)
        );
        accounts.get(0).addCash(100);
        accounts.get(1).addCash(200);
        accounts.get(2).addCash(80);
        var analyticsManager = new hw2.AnalyticsManager(tManager);

        // when
        var rangeFrom = analyticsManager.
                accountsRangeFrom(
                        accounts,
                        accounts.get(0),
                        Comparator.comparing((var a) -> a.balanceOn(LocalDate.now()))
        );

        // then
        assertEquals(accounts.get(0).id(), ((DebitCard) rangeFrom.get(0)).id());
        assertEquals(accounts.get(1).id(), ((DebitCard) rangeFrom.get(1)).id());
    }

    @Test
    public void maxExpenseAmountEntryWithinInterval() {
        // given
        var tManager = new TransactionManager();
        var accounts = List.of(
                new DebitCard(0, tManager),
                new DebitCard(1, tManager),
                new DebitCard(2, tManager)
        );
        accounts.get(0).addCash(100);
        accounts.get(1).addCash(200);
        accounts.get(2).addCash(80);

        accounts.get(0).withdraw(50, accounts.get(1));
        accounts.get(1).withdraw(220, accounts.get(2));
        var analyticsManager = new hw2.AnalyticsManager(tManager);

        // when
        var result = analyticsManager
                .maxExpenseAmountEntryWithinInterval(accounts, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));

        // then
        assertTrue(result.isPresent());
        assertEquals(-220, result.get().getAmount());
    }
}
