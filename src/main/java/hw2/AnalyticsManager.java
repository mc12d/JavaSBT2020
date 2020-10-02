package hw2;

import java.util.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;
    private class ComparatorByAmountDescending implements Comparator<Transaction> {
        @Override
        public int compare(Transaction t, Transaction t1) {
            if (t.getAmount() < t1.getAmount()) { return 1; }
            else if (t.getAmount() == t1.getAmount()) { return 0; }
            else { return -1; }
        }
    }

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        if (account == null) {
            return null;
        }
        Collection<Transaction> transactionsByAccount = transactionManager.findAllTransactionsByAccount(account);
        Map<Account, Integer> occurenceCount = new HashMap<>();
        for (Transaction t : transactionsByAccount) {
            if (t.getBeneficiary() != null) {
                int curCount = occurenceCount.get(t.getBeneficiary());
                occurenceCount.put(t.getBeneficiary(), curCount + 1);
            }
        }
        return Collections.max(occurenceCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }


    public Collection<Transaction> topExpensivePurchases(Account account, int topN) {
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
}
