package liad.com.alcoholcalc.server.beverage;

public class StrongChaser extends Chaser {

    private static BeverageType type = BeverageType.STRONG_CHASER;

    private static double assumedAlcoholConcentration = 40.0;

    public StrongChaser() {
        super.setType(type);
    }

    @Override
    public double getAlcoholConcentration() {
        return assumedAlcoholConcentration;
    }
}
