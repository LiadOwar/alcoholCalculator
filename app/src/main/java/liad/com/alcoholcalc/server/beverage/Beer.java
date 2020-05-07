package liad.com.alcoholcalc.server.beverage;

/**
 * Created by liad on 21/04/2020.
 */

public abstract class Beer extends Beverage {

    protected final static Double assumedConsumptionRate = 22.5;

    @Override
    public double getAssumedConsumptionRate() {
        return assumedConsumptionRate;
    }
}
