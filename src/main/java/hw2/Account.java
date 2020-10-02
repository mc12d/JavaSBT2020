package hw2;

import java.time.LocalDate;
import java.util.Collection;

public class Account {
    private final long id;
    private final TransactionManager transactionManager;
    final Entries entries;

    public Account(long id, TransactionManager transactionManager) {
        this.id = id;
        this.transactionManager = transactionManager;
        this.entries = new Entries();
    }
    /*
    void addEntry(Entry ent) {
        entries.addEntry(ent);
    }

    void removeEntry(Entry ent) {
        entries.
    }
     */

    /**
     * Withdraws money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdraw(double amount, Account beneficiary) {
        if (beneficiary == null) {
            throw new IllegalArgumentException();
        }
        if (amount <= 0 || amount > balanceOn(LocalDate.now())) {
            return false;
        }
        Transaction t = transactionManager.createTransaction(amount, this, beneficiary);
        if (t != null) {
            transactionManager.executeTransaction(t);
            return true;
        }
        return false;
    }

    /**
     * Withdraws cash money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdrawCash(double amount) {
        if (amount <= 0 || amount > balanceOn(LocalDate.now())) {
            return false;
        }
        Transaction t = transactionManager.createTransaction(amount, this, null);
        if (t != null) {
            transactionManager.executeTransaction(t);
            return true;
        }
        return false;
    }

    /**
     * Adds cash money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    public boolean addCash(double amount) {
        if (amount <= 0) {
            return false;
        }
        Transaction t = transactionManager.createTransaction(amount, null, this);
        if (t != null) {
            transactionManager.executeTransaction(t);
            return true;
        }
        return false;
    }


    public Collection<Entry> history(LocalDate from, LocalDate to) {
        return entries.betweenDates(from, to);
    }

    /**
     * Calculates balance on the accounting entries basis
     * @param date
     * @return balance
     */
    public double balanceOn(LocalDate date) {
        Collection<Entry> entriesFrame = entries.to(date);
        double balance = 0;
        for (Entry e : entriesFrame) {
            balance += e.isTransactionRolledBack() ? -e.getAmount() : e.getAmount();
        }
        return balance;
    }

    /**
     * Finds the last transaction of the account and rollbacks it
     */
    public void rollbackLastTransaction() {
        transactionManager.rollbackTransaction(entries.last().getTransaction());
    }

    public Long id() {
        return id;
    }
}

