package liad.com.alcoholcalc.server.beverage;

/**
 * Created by liad on 22/04/2020.
 */

public class StrongBeer extends Beer {

    private static BeverageType type = BeverageType.STRONG_BEER;
    private static double assumedAlcoholConcentration = 8.0;


    @Override
    public double getAlcoholConcentration() {
        return this.assumedAlcoholConcentration;
    }

    @Override
    public BeverageType getType() {
        return type;
    }
}
