package liad.com.alcoholcalc.ui.controller;

import liad.com.alcoholcalc.gateway.Gateway;
import liad.com.alcoholcalc.gateway.GatewayImpl;

/**
 * Created by liad on 07/05/2020.
 */

public class UIControllerImpl implements UIController  {

    private Gateway gateway = new GatewayImpl();


    @Override
    public void addDrinkToSession(String drinkTypeId) {
        gateway.AddDrinkFromUIToServer(drinkTypeId);
    }

    @Override
    public double getAlcoholScore() {
        Double score = gateway.getAlcoholScore();
        return score;
    }
}
