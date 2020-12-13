package hw2;

import java.time.LocalDateTime;

/**
 * The record of allocating the amount to the account
 * Amount can be either positive or negative depending on originator or beneficiary
 */
public class Entry implements Comparable<Entry> {
    private final Account account;
    private final Transaction transaction;
    private final double amount;
    private final LocalDateTime time;

    public Entry(Account account, Transaction transaction, double amount, LocalDateTime time) {
        this.account = account;
        this.transaction = transaction;
        this.amount = amount;
        this.time = time;
    }

    public boolean isTransactionRolledBack() {
        return transaction.isRolledback();
    }

    public int compareTo(Entry e) {
        int comp = time.compareTo(e.time);
        if (comp != 0) {
            return comp;
        }
        // if timestamps are equal, we dont care about entries order,
        // so compare by transaction id
        else {
            if (this.transaction == null && e.transaction == null) {
                return 0;
            } else if (this.transaction == null) {
                return -1;
            } else if (e.transaction == null) {
                return 1;
            } else {
                return (int) (this.transaction.id() - e.transaction.id());
            }
        }
    }

    public double getAmount() {
        return amount;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
