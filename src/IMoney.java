/**
 * Created by Serol on 25.6.2015.
 */
public interface IMoney {
    IMoney add(IMoney money);
    IMoney addMoney(Money money);
    IMoney addMoneyBag(MoneyBag moneyBag);
    boolean isZero();
    IMoney multiply(int factor);
    IMoney negate();
    IMoney subtract(IMoney money);
    void appendTo(MoneyBag moneyBag);
}
