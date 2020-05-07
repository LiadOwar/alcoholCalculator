package liad.com.alcoholcalc.server.beverage;

import com.google.common.collect.Maps;

import java.util.HashMap;

/**
 * Created by liad on 22/04/2020.
 */

public class BeverageFactory {

    private HashMap<BeverageType, Beverage> beverageMap;

    public BeverageFactory() {
        beverageMap = Maps.newHashMap();
        beverageMap.put(BeverageType.STRONG_BEER, new StrongBeer());
    }

    public Beverage getBeverage(BeverageType beverageType){

        Beverage ret = beverageMap.get(beverageType);
        return ret;
    }

}
