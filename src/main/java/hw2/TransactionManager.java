package hw2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages all transactions within the application
 */
public class TransactionManager {

    /**
     * is used for fast search by account
     * we do not delete transaction history, so I don't care about data consistency
     */
    private final Map<Long, Collection<Transaction>> transactions;

    /**
     * Creates and stores transactions
     *
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
        long transactionId = (long) transactions.size();
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
        Transaction rollbacked = transaction.rollback();

        Account originator = rollbacked.getOriginator();
        Account beneficiary = rollbacked.getBeneficiary();
        if (originator != null) {
            originator.entries.addEntry(new Entry(
                    originator,
                    rollbacked,
                    -rollbacked.getAmount(),
                    LocalDateTime.now()
            ));
        }
        if (beneficiary != null) {
            beneficiary.entries.addEntry(new Entry(
                    beneficiary,
                    rollbacked,
                    rollbacked.getAmount(),
                    LocalDateTime.now()
            ));
        }
    }


    public void executeTransaction(Transaction transaction) {
        Transaction executed = transaction.execute();

        Account originator = executed.getOriginator();
        Account beneficiary = executed.getBeneficiary();
        if (originator != null) {
            originator.entries.addEntry(new Entry(
                    originator,
                    executed,
                    -executed.getAmount(),
                    LocalDateTime.now()
            ));
        }
        if (beneficiary != null) {
            beneficiary.entries.addEntry(new Entry(
                    beneficiary,
                    executed,
                    executed.getAmount(),
                    LocalDateTime.now()
            ));
        }
    }
}
