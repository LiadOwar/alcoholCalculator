package liad.com.alcoholcalc.server.beverage;

public abstract class Chaser extends Beverage {

    protected Double assumedConsumptionRate = 30D;

    protected final Double amount = 30D;

    @Override
    public double getAssumedConsumptionRate() {
        return assumedConsumptionRate;
    }

    @Override
    public double getAmount() {
        return amount;
    }
}
