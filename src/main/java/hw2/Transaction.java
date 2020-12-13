package hw2;

public class Transaction {
    private final long id;
    private final double amount;
    private final Account originator;
    private final Account beneficiary;
    private final boolean executed;
    private final boolean rolledBack;


    public Transaction(long id,
                       double amount,
                       Account originator,
                       Account beneficiary,
                       boolean executed,
                       boolean rolledBack) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (originator == null && beneficiary == null) {
            throw new IllegalArgumentException("At least one of the accounts must be non null");
        }
        this.id = id;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = executed;
        this.rolledBack = rolledBack;
    }

    public long id() {
        return id;
    }

    /**
     * Adding entries to both accounts
     *
     * @throws IllegalStateException when was already executed
     */
    public Transaction execute() {
        if (executed) {
            throw new IllegalStateException("Already executed.");
        }
        return new Transaction(
                id,
                amount,
                originator,
                beneficiary,
                true,
                rolledBack
        );
    }

    /**
     * my comment : as I understand, we are not deleting anything from history
     * so I'm adding rollbacked transaction to account entries instead
     * <p>
     * Removes all entries of current transaction from originator and beneficiary
     *
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() {
        if (rolledBack) {
            throw new IllegalStateException("Already rolled back.");
        }
        return new Transaction(
                id,
                amount,
                originator,
                beneficiary,
                executed,
                true
        );
    }

    boolean isRolledback() {
        return rolledBack;
    }

    Account getBeneficiary() {
        return beneficiary;
    }

    Account getOriginator() {
        return originator;
    }

    double getAmount() {
        return amount;
    }


}
