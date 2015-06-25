/**
 * Created by Serol on 25.6.2015.
 */
public class Money implements IMoney {

    private int amount;
    private String currency;

    public Money(int amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

    public IMoney add(IMoney money) {
        return money.addMoney(this);
    }

    public IMoney addMoney(Money money) {
        if (money.currency().equals(currency())) {
            return new Money(amount() + money.amount(), currency());
        }
        return MoneyBag.create(this, money);
    }

    public IMoney addMoneyBag(MoneyBag moneyBag) {
        return moneyBag.addMoney(this);
    }

    public int amount(){
        return amount;
    }

    public String currency(){
        return currency;
    }

    @Override
    public boolean equals(Object anObject) {
        if (isZero()) {
            if (anObject instanceof IMoney) {
                return ((IMoney) anObject).isZero();
            }
        }
        if (anObject instanceof Money) {
            Money aMoney = (Money) anObject;
            return aMoney.currency().equals(currency())
                    && amount() == aMoney.amount();
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (amount == 0) {
            return 0;
        }
        return currency.hashCode() + amount;
    }

    public boolean isZero() {
        return amount() == 0;
    }

    public IMoney multiply(int factor) {
        return new Money(amount()*factor, currency());
    }

    public IMoney negate() {
        return new Money(-amount(), currency());
    }

    public IMoney subtract(IMoney money) {
        return add(money.negate());
    }

    public void appendTo(MoneyBag moneyBag) {
        moneyBag.appendMoney(this);
    }

    @Override
    public String toString() {
        return "[" + amount() + " " + currency() + "]";
    }
}
