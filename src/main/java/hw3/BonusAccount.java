package hw3;

import hw2.DebitCard;
import hw2.Transaction;
import hw2.TransactionManager;

import java.time.LocalDate;

public class BonusAccount extends DebitCard {
    private final double bonusPercent;
    public BonusAccount(long id, TransactionManager transactionManager, double bonusPercent) {
        super(id, transactionManager);
        if (!(bonusPercent >= 0) && (bonusPercent <= 100)) {
            throw new IllegalArgumentException("Bonus percent must be in range [0 - 100]");
        }
        this.bonusPercent = bonusPercent;
    }

    private double calculateBonus(double amount) {
        return bonusPercent / 100 * amount;
    }

    @Override
    public boolean withdraw(double amount, DebitCard beneficiary) {
        if (beneficiary == null) {
            throw new IllegalArgumentException("Unable to withdraw if beneficiary is null. Use withdrawCash().");
        }
        if (amount <= 0 || amount > balanceOn(LocalDate.now())) {
            return false;
        }

        Transaction t = transactionManager.createTransaction(
                amount,
                this,
                beneficiary
        ), t_bonus = transactionManager.createTransaction(
                calculateBonus(amount),
                null,
                this
        );

        if (t != null) {
            transactionManager.executeTransaction(t);
            transactionManager.executeTransaction(t_bonus);
            return true;
        }
        return false;
    }
}
