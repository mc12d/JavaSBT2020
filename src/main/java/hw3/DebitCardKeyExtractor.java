package hw3;


import hw2.DebitCard;

public class DebitCardKeyExtractor implements KeyExtractor<Long, Account> {
    public Long extract(Account x) {
        if (x instanceof DebitCard) {
            return ((DebitCard) x).id();
        }
        return null;
    }
}
