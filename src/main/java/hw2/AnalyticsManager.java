package hw2;

import hw3.Account;
import hw3.KeyExtractor;

import java.time.LocalDate;
import java.util.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    private class ComparatorByAmountDescending implements Comparator<Transaction> {
        @Override
        public int compare(Transaction t, Transaction t1) {
            return Double.compare(t1.getAmount(), t.getAmount());
        }
    }

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public DebitCard mostFrequentBeneficiaryOfAccount(DebitCard account) {
        if (account == null) {
            throw new NullPointerException("Account must be non-null");
        }
        Collection<Transaction> transactionsByAccount = transactionManager.findAllTransactionsByAccount(account);
        Map<DebitCard, Integer> occurrenceCount = new HashMap<>();
        for (Transaction t : transactionsByAccount) {
            if (t.getBeneficiary() != null) {
                int curCount = occurrenceCount.getOrDefault(t.getBeneficiary(), 0);
                occurrenceCount.put(t.getBeneficiary(), curCount + 1);
            }
        }
        return Collections.max(occurrenceCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }


    public Collection<Transaction> topExpensivePurchases(DebitCard account, int topN) {
        if (topN < 0) {
            throw new IllegalArgumentException("topN must be non-negative");
        }
        List<Transaction> transactionsByAccount = (List<Transaction>) transactionManager.findAllTransactionsByAccount(account);
        transactionsByAccount.sort(new ComparatorByAmountDescending());

        ArrayList<Transaction> result = new ArrayList<>();
        for (int i = 0, j = 0; i < transactionsByAccount.size(); i++) {
            if (transactionsByAccount.get(i).getOriginator() == account) {
                result.add(transactionsByAccount.get(i));
                j++;
            }
            if (j == topN) {
                break;
            }
        }
        return result.size() == topN ? result : null;
    }

    public Double overallBalanceOfAccounts(List<? extends Account> accounts) {
        double sum = 0;
        for (var acc : accounts) {
            sum += acc.balanceOn(LocalDate.now());
        }
        return sum;
    }

    public <K> Set<K> uniqueKeysOf(
            List<? extends Account> accounts,
            KeyExtractor<? extends K, ? super Account> extractor) {

        Set<K> keySet = new HashSet<>();
        for (var acc : accounts) {
            keySet.add(extractor.extract(acc));
        }
        return keySet;
    }

    public List<Account> accountsRangeFrom(
            List<? extends Account> accounts,
            Account minAccount,
            Comparator<? super Account> comparator) {

        List<Account> filtered = new ArrayList<>();
        for (var acc : accounts) {
            if (comparator.compare(acc, minAccount) >= 0) {
                filtered.add(acc);
            }
        }
        filtered.sort(comparator);
        return filtered;
    }
}
