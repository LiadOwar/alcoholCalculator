package liad.com.alcoholcalc.ui.controller;

import org.json.JSONException;
import org.json.JSONObject;

import liad.com.alcoholcalc.gateway.Gateway;
import liad.com.alcoholcalc.gateway.GatewayImpl;

/**
 * Created by liad on 07/05/2020.
 */

public class UIControllerImpl implements UIController  {

    private Gateway gateway = new GatewayImpl();


    @Override
    public void addDrinkToSession(String drinkDetails) {
        JSONObject drinkJsonObject = new JSONObject();
        String[] split = drinkDetails.split("_");
        String drinkType = split[0];
        String drinkAmount = split[2];
        try {
            drinkJsonObject.put("type", drinkType);
            drinkJsonObject.put("amount", drinkAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gateway.AddDrinkFromUIToServer(drinkJsonObject);
    }

    @Override
    public double getAlcoholScore() {
        Double score = gateway.getAlcoholScore();
        return score;
    }
}
