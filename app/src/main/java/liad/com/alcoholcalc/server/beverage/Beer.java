package liad.com.alcoholcalc.server.beverage;

/**
 * Created by liad on 21/04/2020.
 */

public abstract class Beer extends Beverage {

    protected final static Double assumedConsumptionRate = 22.5;

    protected Double amount = null;

    @Override
    public double getAssumedConsumptionRate() {
        return assumedConsumptionRate;
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
