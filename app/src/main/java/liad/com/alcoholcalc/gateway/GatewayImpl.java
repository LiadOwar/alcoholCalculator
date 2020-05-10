package liad.com.alcoholcalc.gateway;

import com.google.common.collect.Maps;

import org.joda.time.LocalDateTime;

import java.util.Map;

import liad.com.alcoholcalc.server.ServerController;
import liad.com.alcoholcalc.server.ServerControllerImpl;
import liad.com.alcoholcalc.server.beverage.Beverage;
import liad.com.alcoholcalc.server.beverage.StrongBeer;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;

/**
 * Created by liad on 07/05/2020.
 */

public class GatewayImpl implements Gateway {


    private Map<String, Beverage> beverageConverterMap = Maps.newHashMap();
    private Map<String, String> uIBeverageNameConverterMap = Maps.newHashMap();
    private ServerController serverController = new ServerControllerImpl();

    public GatewayImpl() {
        loadConverterMap();
        loadUiBeverageNameConverterMap();
    }

    private void loadUiBeverageNameConverterMap() {
        uIBeverageNameConverterMap.put("STRONGBEER", "STRONG_BEER");
        uIBeverageNameConverterMap.put("NORMALBEER", "NORMAL_BEER");
        uIBeverageNameConverterMap.put("STRONGCHASER", "STRONG_CHASER");
    }

    @Override
    public void AddDrinkFromUIToServer(String drinkDetails) {

        String drinkTypeInput = drinkDetails.split("_")[0].toUpperCase();
        String amountInput = "500";
        Beverage beverage = convertStringToBeverage(drinkTypeInput);
        SessionDrinkItem drinkItem = new SessionDrinkItem(beverage, Double.parseDouble(amountInput), LocalDateTime.now());
        serverController.addDrinkToSession(drinkItem);
    }

    @Override
    public Double getAlcoholScore() {
        Double score = serverController.getAlcoholScore();
        return score;
    }

    private Beverage convertStringToBeverage(String type) {
        return beverageConverterMap.get(type);
    }

    private void loadConverterMap() {
        beverageConverterMap.put("STRONGBEER", new StrongBeer());
        beverageConverterMap.put("NORMALBEER", new StrongBeer());
        beverageConverterMap.put("STRONGCHASER", new StrongBeer());
    }
}
