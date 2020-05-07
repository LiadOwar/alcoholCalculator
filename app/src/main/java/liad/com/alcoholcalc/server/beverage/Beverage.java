package liad.com.alcoholcalc.server.beverage;

/**
 * Created by liad on 21/04/2020.
 */

public abstract class Beverage {

    protected static BeverageType type;
    protected static double assumedConsumptionRate;
    protected static double assumedAlcoholConcentration;

    public BeverageType getType() {
        return type;
    }

    public void setType(BeverageType type) {
        this.type = type;
    }

    public double getAssumedConsumptionRate() {
        return assumedConsumptionRate;
    }

    public void setAssumedConsumptionRate(double assumedConsumptionRate) {
        this.assumedConsumptionRate = assumedConsumptionRate;
    }

    public abstract double getAlcoholConcentration();

    public void setAlcoholConcentration(double alcoholConcentration) {
        this.assumedAlcoholConcentration = alcoholConcentration;
    }

    @Override
    public boolean equals(Object obj) {
        Beverage otherBeverage = (Beverage)obj;
        if (otherBeverage.getType().equals(this.getType())) {
            return true;
        }

        return false;
    }
}
