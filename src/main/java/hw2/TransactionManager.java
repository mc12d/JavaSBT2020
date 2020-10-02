package hw2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages all transactions within the application
 */
public class TransactionManager {
    // private Collection<Transaction> transactions;

    /**
     is used for fast search by account
     we do not delete transaction history, so I don't care about data consistency
     */
    private final Map<Long, Collection<Transaction>> transactions;

    /**
     * Creates and stores transactions
     * @param amount
     * @param originator
     * @param beneficiary
     * @return created Transaction
     */
    public Transaction createTransaction(double amount,
                                         Account originator,
                                         Account beneficiary) {
        if (originator == null && beneficiary == null) {
            throw new IllegalArgumentException("At least one account must be not null.");
        }
        long transactionId = (long)transactions.size();
        Transaction t = new Transaction(
                transactionId,
                amount,
                originator,
                beneficiary,
                false,
                false
        );
        if (originator != null) {
            transactions.putIfAbsent(originator.id(), new ArrayList<>());
            transactions.get(originator.id()).add(t);
        }
        if (beneficiary != null) {
            transactions.putIfAbsent(beneficiary.id(), new ArrayList<>());
            transactions.get(beneficiary.id()).add(t);
        }
        return t;
    }


    public TransactionManager() {
        this.transactions = new HashMap<>();
    }


    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        return transactions.getOrDefault(account.id(), new ArrayList<>());
    }


    public void rollbackTransaction(Transaction transaction) {
        transaction.rollback();
    }


    public void executeTransaction(Transaction transaction) {
        transaction.execute();
    }
}
