import java.util.ArrayList;
import java.util.List;
//https://github.com/junit-team/junit/blob/master/src/test/java/junit/samples/money/MoneyBag.java
//http://java.dzone.com/articles/generic-repository-and-ddd-rev
/**
 * Created by Serol on 25.6.2015.
 */
public class MoneyBag implements IMoney {
    private List<Money> monies = new ArrayList<Money>(5);

    public static IMoney create(IMoney money1, IMoney money2){
        MoneyBag result = new MoneyBag();
        money1.appendTo(result);
        money2.appendTo(result);
        return result.simplify();
    }

    public IMoney add(IMoney money) {
        return money.addMoneyBag(this);
    }

    public IMoney addMoney(Money money) {
        return MoneyBag.create(money, this);
    }

    public IMoney addMoneyBag(MoneyBag moneyBag) {
        return MoneyBag.create(moneyBag, this);
    }

    void appendBag(MoneyBag moneyBag){
        for (Money each : moneyBag.monies) {
            appendMoney(each);
        }
    }

    void appendMoney(Money money) {
        if (money.isZero()) return;
        IMoney old = findMoney(money.currency());
        if (old == null){
            monies.add(money);
            return;
        }
        monies.remove(old);
        Money sum = (Money)old.add(money);
        if(sum.isZero()){
            return;
        }
        monies.add(sum);
    }

    @Override
    public boolean equals(Object anObject) {
        if (isZero()) {
            if (anObject instanceof IMoney) {
                return ((IMoney) anObject).isZero();
            }
        }

        if (anObject instanceof MoneyBag) {
            MoneyBag aMoneyBag = (MoneyBag) anObject;
            if (aMoneyBag.monies.size() != monies.size()) {
                return false;
            }

            for (Money each : monies) {
                if (!aMoneyBag.contains(each)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean contains(Money money) {
        Money found = findMoney(money.currency());
        if (found == null) return false;
        return found.amount() == money.amount();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (Money each : monies) {
            hash ^= each.hashCode();
        }
        return hash;
    }

    public boolean isZero() {
        return monies.size() == 0;
    }

    public IMoney multiply(int factor) {
        MoneyBag result = new MoneyBag();
        if (factor != 0) {
            for (Money each : monies) {
                result.appendMoney((Money) each.multiply(factor));
            }
        }
        return result;
    }

    public IMoney negate() {
        MoneyBag moneyBag = new MoneyBag();
        for (Money each: monies){
            moneyBag.appendMoney((Money)each.negate());
        }
        return moneyBag;
    }

    public IMoney simplify(){
        if (monies.size() == 1){
            return monies.iterator().next();
        }
        return this;
    }

    public IMoney subtract(IMoney money) {
        return add(money.negate());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Money each : monies) {
            sb.append(each);
        }
        sb.append("}");
        return sb.toString();
    }

    public void appendTo(MoneyBag moneyBag) {
        moneyBag.appendBag(moneyBag);
    }

    private Money findMoney(String currency) {
        for (Money each : monies) {
            if (each.currency().equals(currency)) {
                return each;
            }
        }
        return null;
    }
}
