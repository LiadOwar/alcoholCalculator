package liad.com.alcoholcalc;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import liad.com.alcoholcalc.server.beverage.Beverage;
import liad.com.alcoholcalc.server.beverage.BeverageType;
import liad.com.alcoholcalc.server.beverage.StrongBeer;

import static org.junit.Assert.assertThat;

;

/**
 * Created by liad on 22/04/2020.
 */

public class BeverageFactoryTest extends BaseTest{

    @Test
    public void getStrongBeer_Test() {
        Beverage beverage = beverageFactory.getBeverage(BeverageType.STRONG_BEER);
        assertThat(beverage, CoreMatchers.<Beverage>is(new StrongBeer()));
    }

}
