package liad.com.alcoholcalc.ui.controller;

import org.json.JSONException;
import org.json.JSONObject;

import liad.com.alcoholcalc.gateway.Gateway;
import liad.com.alcoholcalc.gateway.GatewayImpl;

/**
 * Created by liad on 07/05/2020.
 */

public class UIControllerImpl implements UIController {
    private Gateway gateway = new GatewayImpl();

    @Override
    public void addDrinkToSession(String drinkDetails) {
        try {
            JSONObject drinkDetailsObject = new JSONObject(drinkDetails);
            String type = drinkDetailsObject.getString("type");
            Integer amount = drinkDetailsObject.getInt("amount");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
