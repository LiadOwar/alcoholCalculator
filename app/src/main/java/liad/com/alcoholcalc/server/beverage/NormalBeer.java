package liad.com.alcoholcalc.server.beverage;

public class NormalBeer extends Beer {

    private static BeverageType type = BeverageType.NORMAL_BEER;

    private static double assumedAlcoholConcentration = 4.8;

    @Override
    public BeverageType getType() {
        return type;
    }

    @Override
    public double getAlcoholConcentration() {
        return assumedAlcoholConcentration;
    }
}
