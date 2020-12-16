package hw2;

import hw3.Account;
import hw3.KeyExtractor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }


    public DebitCard mostFrequentBeneficiaryOfAccount(DebitCard account) {
        if (account == null) {
            throw new NullPointerException("Account must be non-null");
        }
        return transactionManager.findAllTransactionsByAccount(account)
                .stream()
                .filter(acc -> acc.getBeneficiary() != null)
                .collect(Collectors.groupingBy(Transaction::getBeneficiary, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    public Collection<Transaction> topExpensivePurchases(DebitCard account, int topN) {
        if (topN < 0) {
            throw new IllegalArgumentException("topN must be non-negative");
        }
        return transactionManager.findAllTransactionsByAccount(account)
                .stream()
                .filter(tr -> account.equals(tr.getOriginator()))
                .filter(tr -> tr.getAmount() > topN)
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .collect(Collectors.toList());
    }

    public Double overallBalanceOfAccounts(List<? extends Account> accounts) {
        return accounts
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(acc -> acc.balanceOn(LocalDate.now()))
                .sum();
    }

    public <K> Set<K> uniqueKeysOf(
            List<? extends Account> accounts,
            KeyExtractor<? extends K, ? super Account> extractor) {

        return accounts
                .stream()
                .filter(Objects::nonNull)
                .map(extractor::extract)
                .collect(Collectors.toSet());
    }

    public List<Account> accountsRangeFrom(
            List<? extends Account> accounts,
            Account minAccount,
            Comparator<? super Account> comparator) {

        return accounts.stream()
                .filter(Objects::nonNull)
                .filter(acc -> comparator.compare(acc, minAccount) >= 0)
                .sorted(comparator)
                .collect(Collectors.toList());
    }


    public Optional<Entry> maxExpenseAmountEntryWithinInterval(List<DebitCard> accounts, LocalDate from, LocalDate to) {
        return accounts
                .stream()
                .filter(Objects::nonNull)
                .flatMap(acc -> acc.entries.betweenDates(from, to).stream())
                .filter(ent -> ent.getAmount() < 0)
                .min(Comparator.comparing(Entry::getAmount));
    }
}
