//package liad.com.alcoholcalc;
//
//import android.support.annotation.NonNull;
//
//import junit.framework.Assert;
//
//import org.junit.Test;
//
//import liad.com.alcoholcalc.server.beverage.Beer;
//import liad.com.alcoholcalc.server.beverage.BeerType;
//import liad.com.alcoholcalc.server.beverage.Beverage;
//import liad.com.alcoholcalc.server.beverage.BeverageType;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
///**
// * Created by liad on 21/04/2020.
// */
//
//public class BeverageTest {
//
//    @Test
//    public void createRegularBeer_500ml_Test() {
//        Beverage regularBeer500ml = getBeer(null);
//
//        Assert.assertNotNull(regularBeer500ml);
//    }
//
//    @Test
//    public void getRegularBeerType_500ml_Test() {
//        Beverage regularBeer500ml = getBeer(BeerType.REGULAR);
//        BeverageType type = regularBeer500ml.getType();
//
//        Assert.assertTrue(type.equals(BeverageType.BEER));
//    }
//
//    @Test
//    public void getRegularBeerAlcoholConcentration_500ml_Test() {
//        Beverage regularBeer500ml = getBeer(BeerType.REGULAR);
//        Double alcoholConcentration = regularBeer500ml.getAlcoholConcentration();
//
//        Assert.assertTrue(alcoholConcentration == 4.8);
//    }
//
//    @Test
//    public void getStrongBeerAlcoholConcentration_500ml_Test() {
//        Beverage strongBeer500ml = getBeer(BeerType.STRONG);
//        Double alcoholConcentration = strongBeer500ml.getAlcoholConcentration();
//
//        Assert.assertTrue(alcoholConcentration == 8.0);
//    }
//
//
//    @Test
//    public void getStrongBeerAssumedConsumptionRate_Test() {
//        Beverage strongBeer500ml = getBeer(BeerType.STRONG);
//        double assumedConsumptionRate = strongBeer500ml.getAssumedConsumptionRate();
//
//        assertThat(assumedConsumptionRate, is(0.061));
//    }
//
//    @NonNull
//    private Beverage getBeer(BeerType beerType) {
//        return new Beer(beerType);
//    }
//
//}
